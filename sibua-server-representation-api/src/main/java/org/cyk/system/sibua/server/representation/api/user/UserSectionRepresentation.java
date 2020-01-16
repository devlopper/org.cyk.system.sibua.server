package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserSectionDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserSectionRepresentation.PATH)
public interface UserSectionRepresentation extends RepresentationEntity<UserSectionDto> {
	
	String PATH = "usersection";
	
}
