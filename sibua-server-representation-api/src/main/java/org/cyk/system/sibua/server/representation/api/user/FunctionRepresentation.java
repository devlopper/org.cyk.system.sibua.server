package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.FunctionDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(FunctionRepresentation.PATH)
public interface FunctionRepresentation extends RepresentationEntity<FunctionDto> {
	
	String PATH = "/function";
	
}
