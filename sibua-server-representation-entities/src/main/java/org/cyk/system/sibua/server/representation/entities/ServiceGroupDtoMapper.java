package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ServiceGroupDtoMapper extends AbstractMapperSourceDestinationImpl<ServiceGroupDto, ServiceGroup> {
	private static final long serialVersionUID = 1L;
    
 
}