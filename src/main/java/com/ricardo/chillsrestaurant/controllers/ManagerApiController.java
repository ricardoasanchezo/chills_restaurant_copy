package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.dtos.RegistrationRequest;
import com.ricardo.chillsrestaurant.dtos.MyUserDto;
import com.ricardo.chillsrestaurant.services.RegistrationService;
import com.ricardo.chillsrestaurant.services.MyUserService;
import com.ricardo.chillsrestaurant.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasRole('MANAGER')")
@AllArgsConstructor
public class ManagerApiController
{
    private final MyUserService myUserService;
    private final RegistrationService registrationService;

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('manager:read')")
    public MyUserDto findUserById(@PathVariable int id)
    {
        return myUserService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id)).toUserDto();
    }

    @PutMapping("/getUser/{id}/update")
    @PreAuthorize("hasAuthority('manager:update')")
    public MyUserDto updateProfile(@PathVariable int id, @RequestBody MyUserDto myUserDto)
    {
        return myUserService.managerUpdateUser(id, myUserDto).toUserDto();
    }

    @PutMapping("/getUser/{id}/locked")
    @PreAuthorize("hasAuthority('manager:update')")
    public void setIsLockedById(@PathVariable Integer id, @RequestParam boolean isLocked)
    {
        myUserService.setIsLockedById(id, isLocked);
    }

    @DeleteMapping("/getUser/{id}/delete")
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteUserById(@PathVariable Integer id)
    {
        myUserService.deleteUserById(id);
    }

//    @PutMapping("/lockUser")
//    @PreAuthorize("hasAuthority('manager:update')")
//    public void lockUserById(@RequestParam Long id)
//    {
//        myUserService.setIsLockedById(id, true);
//    }
//
//    @PutMapping("/unlockUser")
//    @PreAuthorize("hasAuthority('manager:update')")
//    public void unlockUserById(@RequestParam Long id)
//    {
//        myUserService.setIsLockedById(id, false);
//    }

    @PostMapping("/addEmployee")
    @PreAuthorize("hasAuthority('manager:create')")
    public ResponseEntity<String> registerEmployee(@RequestBody RegistrationRequest request)
    {
        try
        {
            registrationService.register(request, Role.EMPLOYEE);
            return ResponseEntity.ok("New employee registered successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering new employee");
        }
    }
}
