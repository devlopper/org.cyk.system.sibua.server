package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class DestinationDtoMapper extends AbstractMapperSourceDestinationImpl<DestinationDto, Destination> {
	private static final long serialVersionUID = 1L;
    
 
}