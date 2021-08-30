package com.bank.repository.contracts;

import java.util.List;
import java.util.Optional;

public interface MatcherRepository<DomainObject> {
    Optional<DomainObject> findAnyMatching(DomainObject domainObjectDetails);
    List<DomainObject> findAllMatching(DomainObject domainObjectDetails);
}
