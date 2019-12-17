package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ServiceGroupDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ServiceGroupRepresentation.PATH)
public interface ServiceGroupRepresentation extends RepresentationEntity<ServiceGroupDto> {
	
	String PATH = "/servicegroup";
	
}
