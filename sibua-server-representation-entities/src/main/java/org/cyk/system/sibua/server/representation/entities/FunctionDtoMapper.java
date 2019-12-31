package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Function;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class FunctionDtoMapper extends AbstractMapperSourceDestinationImpl<FunctionDto, Function> {
	private static final long serialVersionUID = 1L;
     
}