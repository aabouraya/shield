package com.knowhow.shield.repository;

import com.knowhow.shield.model.OauthClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsRepository extends CrudRepository<OauthClientDetails, String> {

}
