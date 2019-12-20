package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class TitleDtoMapper extends AbstractMapperSourceDestinationImpl<TitleDto, Title> {
	private static final long serialVersionUID = 1L;
     
}