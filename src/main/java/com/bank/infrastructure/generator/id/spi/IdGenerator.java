package com.bank.infrastructure.generator.id.spi;

public interface IdGenerator<Entity, Id> {
    Id generate(Entity entity);
}
