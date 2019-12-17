package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.LocalisationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(LocalisationRepresentation.PATH)
public interface LocalisationRepresentation extends RepresentationEntity<LocalisationDto> {
	
	String PATH = "/localisation";
	
}
