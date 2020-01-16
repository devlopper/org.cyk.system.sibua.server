package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserActivityDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserActivityRepresentation.PATH)
public interface UserActivityRepresentation extends RepresentationEntity<UserActivityDto> {
	
	String PATH = "useractivity";
	
}
