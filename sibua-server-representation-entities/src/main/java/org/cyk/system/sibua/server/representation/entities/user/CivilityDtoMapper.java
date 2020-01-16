package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.Civility;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class CivilityDtoMapper extends AbstractMapperSourceDestinationImpl<CivilityDto, Civility> {
	private static final long serialVersionUID = 1L;
     
}