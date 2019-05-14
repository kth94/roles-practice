package com.home.kth.rolesprivileges.repository;

import com.home.kth.rolesprivileges.domain.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationRole, Long> {
    ApplicationRole findByName(String name);
}
