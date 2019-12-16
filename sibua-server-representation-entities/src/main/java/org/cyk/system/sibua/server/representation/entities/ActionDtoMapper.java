package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ActionDtoMapper extends AbstractMapperSourceDestinationImpl<ActionDto, Action> {
	private static final long serialVersionUID = 1L;
    
 
}