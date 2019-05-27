package com.knowhow.shield.repository;

import com.knowhow.shield.model.VerificationToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndActiveTrue(String token);
}
