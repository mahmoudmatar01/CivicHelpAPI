package org.civichelpapi.civichelpapi.user.repository;

import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findFirstByRoleAndCityId(Role role, Integer cityId);
    List<User>findAllByRole(Role role);

}

