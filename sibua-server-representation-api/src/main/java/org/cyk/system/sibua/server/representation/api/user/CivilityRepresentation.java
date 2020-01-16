package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.CivilityDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(CivilityRepresentation.PATH)
public interface CivilityRepresentation extends RepresentationEntity<CivilityDto> {
	
	String PATH = "/civility";
	
}
