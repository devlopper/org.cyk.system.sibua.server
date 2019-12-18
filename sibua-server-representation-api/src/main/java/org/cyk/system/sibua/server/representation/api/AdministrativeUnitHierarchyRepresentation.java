package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitHierarchyDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(AdministrativeUnitHierarchyRepresentation.PATH)
public interface AdministrativeUnitHierarchyRepresentation extends RepresentationEntity<AdministrativeUnitHierarchyDto> {
	
	String PATH = AdministrativeUnitRepresentation.PATH+"hierarchy";
	
}
