package com.knowhow.shield.repository;

import com.knowhow.shield.model.OAuthClient;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsRepository extends CrudRepository<OAuthClient, String> {

    List<OAuthClient> findAll();
}
