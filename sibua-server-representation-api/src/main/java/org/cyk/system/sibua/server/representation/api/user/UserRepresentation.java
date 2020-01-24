package org.cyk.system.sibua.server.representation.api.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.system.sibua.server.representation.entities.user.UserDto;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(UserRepresentation.PATH)
public interface UserRepresentation extends RepresentationEntity<UserDto> {
	
	@GET
	@Path(PATH_BUILD_ONE)
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	Response buildIdentificationSheetsReportByIdentifier(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_IS_INLINE) String isInline);
	
	String PATH = "/user";
	
	String PATH_BUILD_ONE = PATH_IDENTIFIER+__SLASH__+"build";
	String PATH_DOWNLOAD_ONE = PATH_IDENTIFIER+__SLASH__+ConstantString.DOWNLOAD;
}
