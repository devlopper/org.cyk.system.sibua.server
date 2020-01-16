package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserFunctionDtoMapper extends AbstractMapperSourceDestinationImpl<UserFunctionDto, UserFunction> {
	private static final long serialVersionUID = 1L;
     
}