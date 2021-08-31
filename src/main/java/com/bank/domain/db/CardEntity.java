package com.bank.domain.db;

import java.util.Objects;

public class CardEntity {
    private Integer id;
    private Integer ownerId;
    private Integer accountId;

    public CardEntity(Integer id, Integer ownerId, Integer accountId) {
        this.id = id;
        this.ownerId = ownerId;
        this.accountId = accountId;
    }

    public CardEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntity that = (CardEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(ownerId, that.ownerId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, accountId);
    }
}
