package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ProgramDtoMapper extends AbstractMapperSourceDestinationImpl<ProgramDto, Program> {
	private static final long serialVersionUID = 1L;
    
 
}