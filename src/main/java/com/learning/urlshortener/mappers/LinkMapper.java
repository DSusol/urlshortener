package com.learning.urlshortener.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.entities.LinkEntity;

@Mapper(componentModel = "spring")
public interface LinkMapper {

    Link linkEntityToLink(LinkEntity linkEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    LinkEntity linkToLinkEntity(Link link);
}
