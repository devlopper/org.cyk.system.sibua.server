package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.FunctionalClassificationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(FunctionalClassificationRepresentation.PATH)
public interface FunctionalClassificationRepresentation extends RepresentationEntity<FunctionalClassificationDto> {
	
	String PATH = "/functionalclassification";
	
}
