package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.dtos.MyUserDto;
import com.ricardo.chillsrestaurant.services.MyUserService;
import com.ricardo.chillsrestaurant.user.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class MyUserController
{
    private final MyUserService myUserService;

    @RequestMapping("/getLoggedInUser")
    public MyUserDto getLoggedInUser()
    {
        return myUserService.getLoggedInUser().toUserDto();
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('employee:read')")
    public List<MyUserDto> getAllUsers()
    {
        return myUserService.findAll().stream().map(MyUser::toUserDto).toList();
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public MyUserDto findUserById(@PathVariable int id)
    {
        return myUserService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id)).toUserDto();
    }

    @PutMapping("/updateLoggedInUser")
    @PreAuthorize("isAuthenticated()")
    public MyUserDto updateLoggedInUser(@RequestBody MyUserDto myUserDto)
    {
        return myUserService.updateLoggedInUser(myUserDto).toUserDto();
    }

    @DeleteMapping("/deleteMyAccount")
    @PreAuthorize("isAuthenticated()")
    public void lockLoggedInUser()
    {
        myUserService.lockLoggedInUser();
    }
}
