package com.bank.domain.db;

import com.bank.policy.account.AccountStatus;

import java.util.Objects;

public class AccountEntity {
    private Integer id;
    private Integer ownerId;
    private Integer cardsTableId;
    private Integer moneyId;
    private AccountStatus status;

    public AccountEntity(Integer id, Integer ownerId, Integer cardsTableId, Integer moneyId, AccountStatus status) {
        this.id = id;
        this.ownerId = ownerId;
        this.cardsTableId = cardsTableId;
        this.moneyId = moneyId;
        this.status = status;
    }

    public AccountEntity() {
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

    public Integer getCardsTableId() {
        return cardsTableId;
    }

    public void setCardsTableId(Integer cardsTableId) {
        this.cardsTableId = cardsTableId;
    }

    public Integer getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Integer moneyId) {
        this.moneyId = moneyId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity accountEntity = (AccountEntity) o;
        return Objects.equals(id, accountEntity.id) && Objects.equals(ownerId, accountEntity.ownerId) && Objects.equals(cardsTableId, accountEntity.cardsTableId) && Objects.equals(moneyId, accountEntity.moneyId) && status == accountEntity.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, cardsTableId, moneyId, status);
    }
}
