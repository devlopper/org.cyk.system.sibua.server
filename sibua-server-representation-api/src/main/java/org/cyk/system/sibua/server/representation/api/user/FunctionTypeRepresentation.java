package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.FunctionTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(FunctionTypeRepresentation.PATH)
public interface FunctionTypeRepresentation extends RepresentationEntity<FunctionTypeDto> {
	
	String PATH = "/functiontype";
	
}
