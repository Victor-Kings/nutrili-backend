package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.email= :searchEmail")
    Optional<User> findByEmail(@Param("searchEmail") String email);

    @Query("select u from User u where u.phone= :searchPhone")
    Optional<User> findByPhone(@Param("searchPhone") String phone);


}
