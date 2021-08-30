package com.bank.domain.db;

import java.util.Objects;

public class Money {

    private Integer id;
    private CurrencyEntity currencyEntity;
    private Integer conventionalUnits;

    public Money(Integer id, CurrencyEntity currencyEntity, Integer conventionalUnits) {
        this.id = id;
        this.currencyEntity = currencyEntity;
        this.conventionalUnits = conventionalUnits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyEntity getCurrency() {
        return currencyEntity;
    }

    public void setCurrency(CurrencyEntity currencyEntity) {
        this.currencyEntity = currencyEntity;
    }

    public Integer getConventionalUnits() {
        return conventionalUnits;
    }

    public void setConventionalUnits(Integer conventionalUnits) {
        this.conventionalUnits = conventionalUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(id, money.id) && Objects.equals(currencyEntity, money.currencyEntity) && Objects.equals(conventionalUnits, money.conventionalUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyEntity, conventionalUnits);
    }
}
