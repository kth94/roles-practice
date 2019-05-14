package com.home.kth.rolesprivileges;

import com.home.kth.rolesprivileges.domain.ApplicationRole;
import com.home.kth.rolesprivileges.domain.Privilege;
import com.home.kth.rolesprivileges.domain.User;
import com.home.kth.rolesprivileges.repository.PrivilegeRepository;
import com.home.kth.rolesprivileges.repository.RoleRepository;
import com.home.kth.rolesprivileges.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataLoaded implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        ApplicationRole adminApplicationRole = roleRepository.findByName("ROLE_ADMIN");

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("test")); //make pbkdf2 hashed
        user.setEmail("test@test.com");
        user.setApplicationRoles(Arrays.asList(adminApplicationRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;

    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private ApplicationRole createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        ApplicationRole applicationRole = roleRepository.findByName(name);
        if (applicationRole == null) {
            applicationRole =  new ApplicationRole(name);
            applicationRole.setPrivileges(privileges);
            roleRepository.save(applicationRole);
        }
        return applicationRole;
    }

}
