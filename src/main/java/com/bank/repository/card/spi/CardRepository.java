package com.bank.repository.card.spi;

import com.bank.domain.db.CardEntity;
import com.bank.repository.contracts.CrudRepository;
import com.bank.repository.contracts.MatcherRepository;

public interface CardRepository extends CrudRepository<CardEntity, Integer>, MatcherRepository<CardEntity> {
}
