package com.bank.repository.account.spi;

import com.bank.domain.db.AccountEntity;
import com.bank.repository.contracts.CrudRepository;
import com.bank.repository.contracts.MatcherRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer>, MatcherRepository<AccountEntity> {

}
