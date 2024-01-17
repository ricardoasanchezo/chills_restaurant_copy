package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.dtos.MyUserDto;
import com.ricardo.chillsrestaurant.repositories.MyUserRepository;
import com.ricardo.chillsrestaurant.user.MyUser;
import com.ricardo.chillsrestaurant.user.Role;
import com.ricardo.chillsrestaurant.utility.InputValidator;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MyUserService implements UserDetailsService
{
    public static final int MAX_FAILED_ATTEMPTS = 3;
    public static final int LOCKED_TIME_IN_MINUTES = 1;

    private final MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
//        return myUserRepository.findByEmail(username).orElseThrow(() ->
//                new UsernameNotFoundException("Username " + username + " not found!"));

        if (InputValidator.containsCharacter(username, '@'))
        {
            return loadMyUserByUsername(username);
        }
        else
        {
            return loadUserByFingerprint(username);
        }
    }

    public UserDetails loadMyUserByUsername(String username) throws UsernameNotFoundException
    {
        return myUserRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found!"));
    }

    public UserDetails loadUserByFingerprint(String fingerprint) throws UsernameNotFoundException
    {
        return myUserRepository.findByFingerprint(fingerprint).orElseThrow(() ->
                new UsernameNotFoundException("No match was found"));
    }

    public UserDetails findUserByFingerprint(UUID fingerprint)
    {
        return null;
    }

    public MyUser getLoggedInUser()
    {
        return (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Optional<MyUser> findById(int id)
    {
        return myUserRepository.findById(id);
    }

    public Optional<MyUser> findByEmail(String email)
    {
        return myUserRepository.findByEmail(email);
    }

    public List<MyUser> findAll()
    {
        Role role = getLoggedInUser().getRole();

        var userList = myUserRepository.findAll();

        if (role == Role.EMPLOYEE)
        {
            userList = userList.stream().filter(user -> user.getRole() != Role.MANAGER).collect(Collectors.toList());
        }

        return userList;
    }

    public List<MyUser> getAllByRole(Role role)
    {
        return myUserRepository.getAllByRole(role);
    }

    public void save(MyUser myUser)
    {
        myUserRepository.save(myUser);
    }

    public boolean exists(Integer id)
    {
        return myUserRepository.existsById(id);
    }

    public void addFailedAttempt(String email)
    {
        MyUser user = myUserRepository.findByEmail(email).orElseThrow();
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        myUserRepository.save(user);
    }

    public void resetFailedAttempts(String email)
    {
        MyUser user = myUserRepository.findByEmail(email).orElseThrow();
        user.setFailedLoginAttempts(0);
        myUserRepository.save(user);
    }

    public void lockUserForFailedLogin(String email)
    {
        // TODO: block ip address instead of user account

        MyUser user = myUserRepository.findByEmail(email).orElseThrow();
        user.setLockUntil(LocalDateTime.now().plusMinutes(LOCKED_TIME_IN_MINUTES));
        user.setIsLocked(true);
        myUserRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(String email)
    {
        MyUser user = myUserRepository.findByEmail(email).orElseThrow();
        if (user.getLockUntil() != null && user.getLockUntil().isBefore(LocalDateTime.now()))
        {
            user.setIsLocked(false);
            user.setLockUntil(null);
            user.setFailedLoginAttempts(0);
            myUserRepository.save(user);

            return true;
        }
        return false;
    }

    public MyUser updateLoggedInUser(MyUserDto dto)
    {
        MyUser user = getLoggedInUser();

        updateUserInfo(dto, user);

        return myUserRepository.save(user);
    }

    private void updateUserInfo(MyUserDto dto, MyUser user)
    {
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail()))
        {
            if (dto.getEmail().matches(InputValidator.EMAIL_REGEX) && myUserRepository.findByEmail(dto.getEmail()).isEmpty())
                user.setEmail(dto.getEmail());
            else
                throw new RuntimeException("This email is already taken by another user: " + dto.getEmail());
        }

        if (dto.getName() != null)
            user.setName(dto.getName());

        if (dto.getPrimaryPhone() != null)
            user.setPrimaryPhone(dto.getPrimaryPhone());

        if (dto.getSecondaryPhone() != null)
            user.setSecondaryPhone(dto.getSecondaryPhone());

        if (dto.getAddress() != null)
            user.setAddress(dto.getAddress());

        if (dto.getPicture() != null)
            user.setPicture(dto.getPicture());
    }

    public MyUser managerUpdateUser(Integer id, MyUserDto dto)
    {
        MyUser user = myUserRepository.findById(id).orElseThrow();

        updateUserInfo(dto, user);

        if (dto.getRole() != null)
        {
            user.setRole(Role.valueOf(dto.getRole()));
        }

        if (dto.getIsLocked() != null)
        {
            user.setIsLocked(dto.getIsLocked());

            if (!dto.getIsLocked()) user.setLockUntil(null);
        }


        return myUserRepository.save(user);
    }

    public void setIsLockedById(Integer id, boolean isLocked)
    {
        MyUser user = myUserRepository.findById(id).orElseThrow();

        user.setIsLocked(isLocked);

        if (!isLocked)
            user.setLockUntil(null);

        myUserRepository.save(user);
    }

    public void deleteUserById(Integer id)
    {
        myUserRepository.deleteById(id);
    }

    public void lockLoggedInUser()
    {
        MyUser user = getLoggedInUser();

        user.setIsLocked(true);

        myUserRepository.save(user);

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
