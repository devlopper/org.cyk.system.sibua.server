package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class FunctionTypeDtoMapper extends AbstractMapperSourceDestinationImpl<FunctionTypeDto, FunctionType> {
	private static final long serialVersionUID = 1L;
     
}