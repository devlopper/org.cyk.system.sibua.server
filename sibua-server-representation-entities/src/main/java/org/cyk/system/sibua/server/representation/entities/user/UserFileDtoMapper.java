package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserFileDtoMapper extends AbstractMapperSourceDestinationImpl<UserFileDto, UserFile> {
	private static final long serialVersionUID = 1L;
     
}