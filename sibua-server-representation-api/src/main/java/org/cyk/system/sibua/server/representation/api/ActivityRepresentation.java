package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ActivityDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ActivityRepresentation.PATH)
public interface ActivityRepresentation extends RepresentationEntity<ActivityDto> {
	
	String PATH = "/activity";
	
}
