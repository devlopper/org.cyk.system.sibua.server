package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class LocalisationDtoMapper extends AbstractMapperSourceDestinationImpl<LocalisationDto, Localisation> {
	private static final long serialVersionUID = 1L;
    
 
}