package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.FunctionCategory;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class FunctionCategoryDtoMapper extends AbstractMapperSourceDestinationImpl<FunctionCategoryDto, FunctionCategory> {
	private static final long serialVersionUID = 1L;

}