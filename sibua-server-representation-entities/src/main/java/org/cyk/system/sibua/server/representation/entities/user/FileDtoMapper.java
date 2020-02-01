package org.cyk.system.sibua.server.representation.entities.user;

import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class FileDtoMapper extends AbstractMapperSourceDestinationImpl<FileDto, File> {
	private static final long serialVersionUID = 1L;
    
	@Override
	public FileDto getSource(File destination, Properties properties) {
		FileDto source = super.getSource(destination, properties);
		source.setBytes(null);
		return source;
	}
	
}