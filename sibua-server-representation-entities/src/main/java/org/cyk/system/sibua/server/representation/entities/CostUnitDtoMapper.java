package org.cyk.system.sibua.server.representation.entities;

import org.cyk.system.sibua.server.persistence.entities.CostUnit;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class CostUnitDtoMapper extends AbstractMapperSourceDestinationImpl<CostUnitDto, CostUnit> {
	private static final long serialVersionUID = 1L;
     
}