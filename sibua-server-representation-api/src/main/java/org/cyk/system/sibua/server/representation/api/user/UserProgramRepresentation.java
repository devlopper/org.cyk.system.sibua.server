package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserProgramDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserProgramRepresentation.PATH)
public interface UserProgramRepresentation extends RepresentationEntity<UserProgramDto> {
	
	String PATH = "userprogram";
	
}
