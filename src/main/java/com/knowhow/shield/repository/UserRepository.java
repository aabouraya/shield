package com.knowhow.shield.repository;

import com.knowhow.shield.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends NaturalRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);
}
