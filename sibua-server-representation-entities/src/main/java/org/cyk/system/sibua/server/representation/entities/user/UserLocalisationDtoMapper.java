package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserLocalisationDtoMapper extends AbstractMapperSourceDestinationImpl<UserLocalisationDto, UserLocalisation> {
	private static final long serialVersionUID = 1L;
     
}