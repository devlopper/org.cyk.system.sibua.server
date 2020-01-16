package org.cyk.system.sibua.server.representation.api.user;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.user.UserAdministrativeUnitDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserAdministrativeUnitRepresentation.PATH)
public interface UserAdministrativeUnitRepresentation extends RepresentationEntity<UserAdministrativeUnitDto> {
	
	String PATH = "useradministrativeunit";
	
}
