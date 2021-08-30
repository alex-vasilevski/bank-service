package com.bank.repository.contracts;

import com.bank.repository.exceptions.spi.AttemptToOverrideEntityIdException;
import com.bank.repository.exceptions.spi.CannotReadEntityWithNullableId;
import com.bank.repository.exceptions.spi.CannotStoreEntityWithNullableIdException;

import java.util.Optional;

public interface CrudRepository<DomainObject, Id> {
    void create(DomainObject domainObjectDetails);
    Optional<DomainObject> readById(Id id) throws CannotReadEntityWithNullableId;
    Optional<DomainObject> update(DomainObject source, Id id) throws CannotReadEntityWithNullableId, AttemptToOverrideEntityIdException;
    void delete(Id id) throws CannotReadEntityWithNullableId;
}
