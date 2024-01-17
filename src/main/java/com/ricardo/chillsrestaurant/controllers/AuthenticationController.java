package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.dtos.ModalResponse;
import com.ricardo.chillsrestaurant.dtos.RegistrationRequest;
import com.ricardo.chillsrestaurant.services.RegistrationService;
import com.ricardo.chillsrestaurant.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController
{
    private final RegistrationService registrationService;
    // private final FingerprintAuthenticationProvider fingerAuthProvider;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

//    @GetMapping("/employee/login")
//    public String employeeLogin()
//    {
//        return "login-alternate";
//    }
//
//    @PostMapping("/employee/login")
//    public String employeeLoginHandler(@RequestBody FingerprintLoginRequest request)
//    {
//        Authentication authenticationToken = new FingerprintAuthenticationToken(request.getFingerprint(), null);
//
//        Authentication authentication = fingerAuthProvider.authenticate(authenticationToken);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "homepage";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request)
//    {
//        try
//        {
//            registrationService.register(request, Role.USER);
//            return ResponseEntity.status(HttpStatus.OK).body("");
//        }
//        catch (Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
//        }
//    }

    @ResponseBody
    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModalResponse registerUser(@RequestBody RegistrationRequest request)
    {
        try
        {
            return registrationService.register(request, Role.USER);
        }
        catch (Exception e)
        {
            return new ModalResponse("Registration Failed", "There was a server error while registering");
        }
    }

    @GetMapping("/register")
    public String registerUser()
    {
        return "user-registration";
    }
}
