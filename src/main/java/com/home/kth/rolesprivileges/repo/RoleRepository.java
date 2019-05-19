package com.home.kth.rolesprivileges.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.kth.rolesprivileges.domain.Rolee;

@Repository
public interface RoleRepository extends JpaRepository<Rolee, Long>{
	Rolee findByName(String name);
}
