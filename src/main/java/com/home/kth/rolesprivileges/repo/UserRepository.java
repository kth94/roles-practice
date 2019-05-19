package com.home.kth.rolesprivileges.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.kth.rolesprivileges.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
