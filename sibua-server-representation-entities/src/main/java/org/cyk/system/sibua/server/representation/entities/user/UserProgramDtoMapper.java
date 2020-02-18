package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserProgram;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserProgramDtoMapper extends AbstractMapperSourceDestinationImpl<UserProgramDto, UserProgram> {
	private static final long serialVersionUID = 1L;
     
}