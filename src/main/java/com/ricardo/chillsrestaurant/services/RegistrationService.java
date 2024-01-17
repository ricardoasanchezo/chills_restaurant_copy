package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.dtos.ModalResponse;
import com.ricardo.chillsrestaurant.dtos.RegistrationRequest;
import com.ricardo.chillsrestaurant.repositories.MyUserRepository;
import com.ricardo.chillsrestaurant.user.MyUser;
import com.ricardo.chillsrestaurant.user.Role;
import com.ricardo.chillsrestaurant.utility.InputValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService
{
    private final MyUserRepository myUserRepository;
    private final MyUserService myUserService;
    private final PasswordEncoder passwordEncoder;

    public ModalResponse register(RegistrationRequest request, Role role)
    {
        var rf = "Registration failed!";

        if (myUserRepository.findByEmail(request.getEmail()).isPresent())
        {
            // throw new Exception("User already exists");
            return new ModalResponse(rf, "The email is already taken: " + request.getEmail());
        }

//        if (!validateRegistrationRequest(request))
//        {
//            throw new Exception("Request isn't valid");
//        }

        if (request.getName().isBlank())
            return new ModalResponse(rf, "The provided name is invalid");

        if (!request.getEmail().matches(InputValidator.EMAIL_REGEX))
            return new ModalResponse(rf, "The provided email is invalid");

        if (request.getPassword().isBlank())
            return new ModalResponse(rf, "The provided password is sad bro");

        if (!request.getPrimaryPhone().matches(InputValidator.PHONE_NUMBER_REGEX))
            return new ModalResponse(rf, "The provided primary phone number is invalid");

        if (request.getAddress().isBlank())
            return new ModalResponse(rf, "You need to provide an address");

        MyUser newCustomer = new MyUser(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                role,
                true,
                false,
                request.getName(),
                request.getPrimaryPhone(),
                request.getSecondaryPhone(),
                request.getAddress(),
                "",
                0,
                null,
                null
        );

        myUserService.save(newCustomer);

        return new ModalResponse("Registration successful", "Your account was created successfully!");
    }
}
