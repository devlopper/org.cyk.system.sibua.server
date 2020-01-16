package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.FileDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(FileRepresentation.PATH)
public interface FileRepresentation extends RepresentationEntity<FileDto> {
	
	String PATH = "file";
	
}
