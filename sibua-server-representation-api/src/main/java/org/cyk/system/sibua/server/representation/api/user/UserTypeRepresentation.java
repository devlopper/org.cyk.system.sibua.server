package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserTypeRepresentation.PATH)
public interface UserTypeRepresentation extends RepresentationEntity<UserTypeDto> {
	
	String PATH = "/usertype";
	
}
