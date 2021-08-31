package com.bank.repository.contracts;

import java.util.Optional;

public interface CrudRepository<DomainObject, Id> {
    void create(DomainObject domainObjectDetails);
    Optional<DomainObject> readById(Id id);
    void update(DomainObject source, Id id);
    void delete(Id id);
}
