package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class FileDtoMapper extends AbstractMapperSourceDestinationImpl<FileDto, File> {
	private static final long serialVersionUID = 1L;
     
}