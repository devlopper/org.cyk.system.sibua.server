package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitRepresentation.PATH)
public interface AdministrativeUnitRepresentation extends RepresentationEntity<AdministrativeUnitDto> {
	
	String PATH = "/administrativeunit";
	
}
