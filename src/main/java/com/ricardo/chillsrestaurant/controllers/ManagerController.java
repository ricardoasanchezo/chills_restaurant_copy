package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.services.MyUserService;
import com.ricardo.chillsrestaurant.user.MyUser;
import com.ricardo.chillsrestaurant.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
@AllArgsConstructor
public class ManagerController
{
    private final MyUserService myUserService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('manager:read')")
    public String managerDashboard()
    {
        return "manager";
    }

    @GetMapping("/view-users")
    @PreAuthorize("hasAuthority('manager:read')")
    public String viewUserList(Model model)
    {
        return "view-users";
    }

    @GetMapping("/view-users/{id}")
    @PreAuthorize("hasAuthority('manager:read')")
    public String viewUserProfile(Model model, @PathVariable Integer id)
    {
        MyUser user = myUserService.findById(id).orElseThrow();
        model.addAttribute(user);

        model.addAttribute("roles", Role.getNames());
        return "manager-profile-view";
    }
}
