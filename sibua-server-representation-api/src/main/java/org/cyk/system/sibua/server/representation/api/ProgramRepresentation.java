package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ProgramDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ProgramRepresentation.PATH)
public interface ProgramRepresentation extends RepresentationEntity<ProgramDto> {
	
	String PATH = "/program";
	
}
