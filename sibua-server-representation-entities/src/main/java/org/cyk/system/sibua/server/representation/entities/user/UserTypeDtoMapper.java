package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserType;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserTypeDtoMapper extends AbstractMapperSourceDestinationImpl<UserTypeDto, UserType> {
	private static final long serialVersionUID = 1L;
     
}