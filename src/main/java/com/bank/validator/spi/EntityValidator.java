package com.bank.validator.spi;

public interface EntityValidator<DomainEntity> {
    public boolean isValid(DomainEntity domainEntity);
}
