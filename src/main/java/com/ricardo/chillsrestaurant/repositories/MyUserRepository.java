package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.user.MyUser;
import com.ricardo.chillsrestaurant.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface MyUserRepository extends JpaRepository<MyUser, Integer>
{
    Optional<MyUser> findByEmail(String email);

    Optional<MyUser> findByFingerprint(String fingerprint);

    List<MyUser> getAllByRole(Role role);

    @Query("UPDATE MyUser u SET u.isLocked = ?2 WHERE u.id = ?1")
    void setIsLockedById(Long id, boolean isLocked);
}
