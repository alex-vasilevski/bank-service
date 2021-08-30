package com.bank.domain.db;

import com.bank.domain.db.operators.internal.Client;

import java.util.Objects;

public class CardEntity {
    private Integer id;
    private Client owner;
    private AccountEntity accountEntity;

    public CardEntity(Integer id, Client owner, AccountEntity accountEntity) {
        this.id = id;
        this.owner = owner;
        this.accountEntity = accountEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public AccountEntity getAccount() {
        return accountEntity;
    }

    public void setAccount(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntity cardEntity = (CardEntity) o;
        return Objects.equals(id, cardEntity.id) && Objects.equals(owner, cardEntity.owner) && Objects.equals(accountEntity, cardEntity.accountEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, accountEntity);
    }
}
