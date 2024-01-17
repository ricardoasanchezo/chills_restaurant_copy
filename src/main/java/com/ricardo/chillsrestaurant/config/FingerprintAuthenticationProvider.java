package com.ricardo.chillsrestaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

public class FingerprintAuthenticationProvider implements AuthenticationProvider
{
    private final UserDetailsService userDetailsService;

    public FingerprintAuthenticationProvider(UserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String fingerprint = authentication.getPrincipal().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(fingerprint);

        if (userDetails == null)
        {
            throw new UsernameNotFoundException("Fingerprint does not match any user");
        }
        
        if (!userDetails.isAccountNonLocked())
        {
            throw new LockedException("User is locked");
        }

        return new FingerprintAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return FingerprintAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
