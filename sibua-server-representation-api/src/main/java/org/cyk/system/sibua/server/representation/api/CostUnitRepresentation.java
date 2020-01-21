package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.CostUnitDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(CostUnitRepresentation.PATH)
public interface CostUnitRepresentation extends RepresentationEntity<CostUnitDto> {
	
	String PATH = "costunit";
	
}
