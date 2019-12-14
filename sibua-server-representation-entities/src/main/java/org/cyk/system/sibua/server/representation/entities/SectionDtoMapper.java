package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class SectionDtoMapper extends AbstractMapperSourceDestinationImpl<SectionDto, Section> {
	private static final long serialVersionUID = 1L;
    
 
}