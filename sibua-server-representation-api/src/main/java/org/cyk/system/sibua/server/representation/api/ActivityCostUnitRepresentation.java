package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.ActivityCostUnitDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(ActivityCostUnitRepresentation.PATH)
public interface ActivityCostUnitRepresentation extends RepresentationEntity<ActivityCostUnitDto> {
	
	String PATH = "activitycostunit";
	
}
