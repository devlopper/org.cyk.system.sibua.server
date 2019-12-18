package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.DestinationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(DestinationRepresentation.PATH)
public interface DestinationRepresentation extends RepresentationEntity<DestinationDto> {
	
	String PATH = "/destination";
	
}
