package com.home.kth.rolesprivileges;

import com.home.kth.rolesprivileges.domain.ApplicationRole;
import com.home.kth.rolesprivileges.domain.Privilege;
import com.home.kth.rolesprivileges.domain.User;
import com.home.kth.rolesprivileges.repository.RoleRepository;
import com.home.kth.rolesprivileges.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(" ", " ", true,
                                                                        true, true, true,
                                                                        getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getApplicationRoles());
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<ApplicationRole> applicationRoles) {
        return getGrantedAuthorities(getPrivileges(applicationRoles));
    }

    private List<String> getPrivileges(Collection<ApplicationRole> applicationRoles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for (ApplicationRole applicationRole : applicationRoles) {
            collection.addAll(applicationRole.getPrivileges());
        }

        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
