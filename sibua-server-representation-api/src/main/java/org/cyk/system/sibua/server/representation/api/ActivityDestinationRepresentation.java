package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ActivityDestinationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ActivityDestinationRepresentation.PATH)
public interface ActivityDestinationRepresentation extends RepresentationEntity<ActivityDestinationDto> {
	
	String PATH = "/destinationactivity";
	
}
