package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitFunctionTypeDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitFunctionTypeRepresentation.PATH)
public interface AdministrativeUnitFunctionTypeRepresentation extends RepresentationEntity<AdministrativeUnitFunctionTypeDto> {
	
	String PATH = "administrativeunitfunctiontype";
	
}
