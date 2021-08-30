package com.bank.domain.db.operators;

import com.bank.policy.users.rights.Right;

import java.util.Set;

public class Operator {

    private Integer id;
    private String name;
    private String lastName;
    private Set<Right> rights;

    public Operator(Integer id, String name, String lastName, Set<Right> rights) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.rights = rights;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }
}
