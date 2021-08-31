package com.bank.infrastructure.factory.generator.id.spi;

public interface IdGenerator<Entity, Id> {
    Id generate(Entity entity);
}
