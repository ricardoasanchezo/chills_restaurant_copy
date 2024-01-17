package com.ricardo.chillsrestaurant.config;

import com.ricardo.chillsrestaurant.services.MyUserService;
import com.ricardo.chillsrestaurant.user.MyUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
    private final MyUserService myUserService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ?autoplay=1";
        String email = request.getParameter("username");

        Optional<MyUser> userOptional = myUserService.findByEmail(email);

        if (userOptional.isEmpty())
        {
            url = "http://localhost:8080/auth/login?error";
        }
        else
        {
            MyUser user = userOptional.get();

            if (user.isEnabled() && user.isAccountNonLocked())
            {
                if (user.getFailedLoginAttempts() < MyUserService.MAX_FAILED_ATTEMPTS - 1)
                {
                    myUserService.addFailedAttempt(email);
                    url = "http://localhost:8080/auth/login?error";
                }
                else
                {
                    myUserService.lockUserForFailedLogin(email);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.");
                    url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ?autoplay=1";
                }
            }
            else if (!user.isAccountNonLocked())
            {
                if (myUserService.unlockWhenTimeExpired(email))
                {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                    url = "http://localhost:8080/auth/login";
                }
            }
        }

        super.setDefaultFailureUrl(url);
        super.onAuthenticationFailure(request, response, exception);
    }
}
