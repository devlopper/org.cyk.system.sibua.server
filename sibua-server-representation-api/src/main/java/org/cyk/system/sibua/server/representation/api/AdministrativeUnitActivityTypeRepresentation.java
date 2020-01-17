package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitActivityTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitActivityTypeRepresentation.PATH) @Deprecated
public interface AdministrativeUnitActivityTypeRepresentation extends RepresentationEntity<AdministrativeUnitActivityTypeDto> {
	
	String PATH = "administrativeunitactivitytype";
	
}
