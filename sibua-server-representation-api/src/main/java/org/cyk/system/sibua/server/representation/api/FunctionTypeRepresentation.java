package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.FunctionTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(FunctionTypeRepresentation.PATH)
public interface FunctionTypeRepresentation extends RepresentationEntity<FunctionTypeDto> {
	
	String PATH = "/functiontype";
	
}
