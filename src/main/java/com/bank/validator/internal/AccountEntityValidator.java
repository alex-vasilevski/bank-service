package com.bank.validator.internal;

import com.bank.domain.db.AccountEntity;
import com.bank.validator.spi.EntityValidator;

public class AccountEntityValidator implements EntityValidator<AccountEntity> {
    @Override
    public boolean isValid(AccountEntity accountEntity) {
        if(accountEntity == null || accountEntity.getStatus() == null || accountEntity.getId() == null){
            return false;
        }
        return true;
    }
}
