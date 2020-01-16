package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserActivityDtoMapper extends AbstractMapperSourceDestinationImpl<UserActivityDto, UserActivity> {
	private static final long serialVersionUID = 1L;
     
}