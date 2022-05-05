package com.learning.urlshortener.database.links;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.learning.urlshortener.domain.Link;

@Mapper(componentModel = "spring")
public interface LinkEntityMapper {

    LinkEntityMapper INSTANCE = Mappers.getMapper(LinkEntityMapper.class);
    Link linkEntityToLink(LinkEntity linkEntity);

    @Mapping(target = "customer", ignore = true)
    LinkEntity linkToLinkEntity(Link link);
}
