package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.UserFileDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserFileRepresentation.PATH)
public interface UserFileRepresentation extends RepresentationEntity<UserFileDto> {
	
	String PATH = "/userfile";
	
}
