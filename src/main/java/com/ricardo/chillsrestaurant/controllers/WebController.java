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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class WebController
{
    private final MyUserService myUserService;

    @GetMapping("/homepage")
    public String homepage()
    {
        return "homepage";
    }

    @GetMapping("/menu")
    public String menu()
    {
        return "menu";
    }

    @GetMapping("/menu-employees")
    public String employeeMenu()
    {
        return "menu-employees";
    }

    @GetMapping("/")
    public RedirectView empty()
    {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/homepage");
        return redirectView;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model)
    {
        MyUser user = myUserService.getLoggedInUser();
        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("/orders/tickets")
    public String orders()
    {
        return "order-tickets";
    }

    @GetMapping("/orders")
    public String viewOrders()
    {
        return "view-orders";
    }

    @GetMapping("/view-users")
    @PreAuthorize("hasAuthority('employee:read')")
    public String viewUserList(Model model)
    {
        return "view-users";
    }

    @GetMapping("/view-users/{id}")
    @PreAuthorize("hasAuthority('employee:read')")
    public String viewUserProfile(Model model, @PathVariable Integer id)
    {
        MyUser user = myUserService.findById(id).orElseThrow();
        model.addAttribute(user);

        model.addAttribute("roles", Role.getNames());
        return "manager-profile-view";
    }
}
