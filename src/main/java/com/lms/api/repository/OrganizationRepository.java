package com.lms.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.api.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID>{

	Optional<Organization> findByName(String name);
	
}
