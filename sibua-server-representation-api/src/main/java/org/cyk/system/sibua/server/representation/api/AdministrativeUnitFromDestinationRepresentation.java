package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitFromDestinationDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitFromDestinationRepresentation.PATH)
public interface AdministrativeUnitFromDestinationRepresentation extends RepresentationEntity<AdministrativeUnitFromDestinationDto> {
	
	String PATH = "/administrativeunitfromdestination";
	
}
