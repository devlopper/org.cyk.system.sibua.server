package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserFunctionDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserFunctionRepresentation.PATH)
public interface UserFunctionRepresentation extends RepresentationEntity<UserFunctionDto> {
	
	String PATH = "userfunction";
	
}
