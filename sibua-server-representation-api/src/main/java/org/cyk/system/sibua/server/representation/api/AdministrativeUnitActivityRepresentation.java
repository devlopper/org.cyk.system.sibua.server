package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitActivityDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitActivityRepresentation.PATH)
public interface AdministrativeUnitActivityRepresentation extends RepresentationEntity<AdministrativeUnitActivityDto> {
	
	String PATH = "/administrativeunitactivity";
	
}
