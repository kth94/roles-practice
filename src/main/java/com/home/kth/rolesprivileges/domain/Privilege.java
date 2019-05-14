package com.home.kth.rolesprivileges.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<ApplicationRole> applicationRoles;

    public Privilege(String name) {
        this.name = name;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ApplicationRole> getApplicationRoles() {
        return applicationRoles;
    }

    public void setApplicationRoles(Collection<ApplicationRole> applicationRoles) {
        this.applicationRoles = applicationRoles;
    }
}
