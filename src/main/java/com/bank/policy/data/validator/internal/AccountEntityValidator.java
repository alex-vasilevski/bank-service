package com.bank.policy.data.validator.internal;

import com.bank.domain.db.AccountEntity;
import com.bank.policy.data.validator.spi.EntityValidator;

public class AccountEntityValidator implements EntityValidator<AccountEntity> {
    @Override
    public boolean isValid(AccountEntity accountEntity) {
        if(accountEntity == null || accountEntity.getStatus() == null || accountEntity.getId() == null){
            return false;
        }
        return true;
    }
}
