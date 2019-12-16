package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ActionDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ActionRepresentation.PATH)
public interface ActionRepresentation extends RepresentationEntity<ActionDto> {
	
	String PATH = "/action";
	
}
