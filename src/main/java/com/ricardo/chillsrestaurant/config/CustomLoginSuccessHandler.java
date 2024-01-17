package com.ricardo.chillsrestaurant.config;

import com.ricardo.chillsrestaurant.services.MyUserService;
import com.ricardo.chillsrestaurant.user.MyUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    private final MyUserService myUserService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        MyUser user = (MyUser) authentication.getPrincipal();
        if (user.getFailedLoginAttempts() > 0)
            myUserService.resetFailedAttempts(user.getEmail());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}


