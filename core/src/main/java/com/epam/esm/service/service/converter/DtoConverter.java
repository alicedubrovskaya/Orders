package com.epam.esm.service.service.converter;

public interface DtoConverter<Entity, DtoEntity> {
    DtoEntity convertToDto(Entity entity);

    Entity convertToEntity(DtoEntity dtoEntity, Entity entityToUpdate);
}
