package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.User;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserDtoMapper extends AbstractMapperSourceDestinationImpl<UserDto, User> {
	private static final long serialVersionUID = 1L;
     
}