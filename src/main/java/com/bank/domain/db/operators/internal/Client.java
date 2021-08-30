package com.bank.domain.db.operators.internal;

import com.bank.domain.db.AccountEntity;
import com.bank.domain.db.operators.Operator;
import com.bank.policy.users.rights.Right;

import java.util.Objects;
import java.util.Set;


public class Client extends Operator {
    private Integer id;
    private Set<AccountEntity> accountEntities;

    public Client(Integer id, String name, String lastName,
                  Set<Right> rights, Integer id1, Set<AccountEntity> accountEntities) {
        super(id, name, lastName, rights);
        this.id = id1;
        this.accountEntities = accountEntities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<AccountEntity> getAccounts() {
        return accountEntities;
    }

    public void setAccounts(Set<AccountEntity> accountEntities) {
        this.accountEntities = accountEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(accountEntities, client.accountEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountEntities);
    }
}
