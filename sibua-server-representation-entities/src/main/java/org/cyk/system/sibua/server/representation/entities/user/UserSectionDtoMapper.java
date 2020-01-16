package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserSectionDtoMapper extends AbstractMapperSourceDestinationImpl<UserSectionDto, UserSection> {
	private static final long serialVersionUID = 1L;
     
}