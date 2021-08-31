package com.bank.validator.internal;

import com.bank.domain.db.CardEntity;
import com.bank.validator.spi.EntityValidator;

public class CardEntityValidator implements EntityValidator<CardEntity> {
    @Override
    public boolean isValid(CardEntity cardEntity) {
        return false;
    }
}
