package org.civichelpapi.civichelpapi.auth.repository;

import org.civichelpapi.civichelpapi.auth.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokedTokenRepository
        extends JpaRepository<RevokedToken, Long> {

    boolean existsByToken(String token);

}