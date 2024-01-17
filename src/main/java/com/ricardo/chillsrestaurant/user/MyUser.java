package com.ricardo.chillsrestaurant.user;

import com.ricardo.chillsrestaurant.dtos.MyUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MyUser implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isEnabled;
    private Boolean isLocked;
    private String name;
    private String primaryPhone;
    private String secondaryPhone;
    private String address;
    private String picture; // saves name or path of the picture
    private Integer failedLoginAttempts;
    private LocalDateTime lockUntil;

    @Column(unique = true)
    private String fingerprint;

    public Integer getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrimaryPhone()
    {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone)
    {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone()
    {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone)
    {
        this.secondaryPhone = secondaryPhone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public LocalDateTime getLockUntil()
    {
        return lockUntil;
    }

    public void setLockUntil(LocalDateTime lockUntil)
    {
        this.lockUntil = lockUntil;
    }

    public Integer getFailedLoginAttempts()
    {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer count)
    {
        failedLoginAttempts = count;
    }

    public Boolean getIsLocked()
    {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked)
    {
        this.isLocked = isLocked;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public String getFingerprint()
    {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint)
    {
        this.fingerprint = fingerprint;
    }

    public MyUserDto toUserDto()
    {
        return new MyUserDto(
                this.id,
                this.email,
                this.role.name(),
                this.isEnabled,
                this.isLocked,
                this.name,
                this.primaryPhone,
                this.secondaryPhone,
                this.address,
                this.picture
        );
    }

    // USER DETAILS INTERFACE METHODS ============================================================================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return role.getAuthorities();
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }
}
