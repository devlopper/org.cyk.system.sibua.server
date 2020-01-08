package org.cyk.system.sibua.server.representation.api;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitDto;
import org.cyk.utility.server.representation.RepresentationEntity;

import io.swagger.annotations.ApiOperation;

@Path(AdministrativeUnitRepresentation.PATH)
public interface AdministrativeUnitRepresentation extends RepresentationEntity<AdministrativeUnitDto> {
	
	@POST
	@Path("generatecodesbysectionscodes")
	@ApiOperation(value = "Generate administratives units codes by sections codes")
	Response generateCodesBySectionsCodes(@QueryParam("section") List<String> sectionsCodes);
	
	String PATH = "/administrativeunit";
	
}
