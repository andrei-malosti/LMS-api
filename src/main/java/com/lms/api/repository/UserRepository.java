package com.lms.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.api.entity.Role;
import com.lms.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	@Query("SELECT u FROM User u WHERE u.email = :email " +
		       "AND (:orgId IS NULL OR u.organization.id = :orgId)")
	Optional<User> findByEmailInOrganization(String email, UUID orgId);
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role = :role AND u.organization.id = :organizationId ")
	Optional<User> findByUUID(Role role, UUID organizationId);
	
	boolean existsByEmail(String email);
	
	
}
