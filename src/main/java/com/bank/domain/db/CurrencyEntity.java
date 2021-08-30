package com.bank.domain.db;

import com.bank.policy.currency.CurrencyType;

import java.util.Objects;

public class CurrencyEntity {

    private Integer id;
    private CurrencyType currencyType;
    private Long conversionIndex;

    public CurrencyEntity(Integer id, CurrencyType currencyType, Long conversionIndex) {
        this.id = id;
        this.currencyType = currencyType;
        this.conversionIndex = conversionIndex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Long getConversionIndex() {
        return conversionIndex;
    }

    public void setConversionIndex(Long conversionIndex) {
        this.conversionIndex = conversionIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity currencyEntity = (CurrencyEntity) o;
        return Objects.equals(id, currencyEntity.id) && Objects.equals(currencyType, currencyEntity.currencyType) && Objects.equals(conversionIndex, currencyEntity.conversionIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyType, conversionIndex);
    }
}
