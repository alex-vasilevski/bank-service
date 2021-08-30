package com.bank.policy.data.validator.spi;

public interface EntityValidator<DomainEntity> {
    public boolean isValid(DomainEntity domainEntity);
}
