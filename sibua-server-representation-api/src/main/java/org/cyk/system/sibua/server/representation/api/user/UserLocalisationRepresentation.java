package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserLocalisationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserLocalisationRepresentation.PATH)
public interface UserLocalisationRepresentation extends RepresentationEntity<UserLocalisationDto> {
	
	String PATH = "userlocalisation";
	
}
