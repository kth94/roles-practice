package com.home.kth.rolesprivileges.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.kth.rolesprivileges.domain.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{
	Privilege findByName(String name);
}
