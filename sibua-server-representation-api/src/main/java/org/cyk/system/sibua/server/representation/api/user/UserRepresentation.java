package org.cyk.system.sibua.server.representation.api.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	
	@POST
	@Path("notifyaccesstoken")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	Response notifyAccessToken(@QueryParam(PARAMETER_IDENTIFIER) List<String> usersIdentifiers);
	
	@GET
	@Path(PATH_BUILD_ONE)
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	Response buildIdentificationSheetsReportByIdentifier(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_IS_INLINE) String isInline);
	
	@GET
	@Path(PATH_GET_FILE)
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	Response getFileByIdentifier(@PathParam(PARAMETER_IDENTIFIER) String identifier,@PathParam(PARAMETER_TYPE) String type,@QueryParam(PARAMETER_IS_INLINE) String isInline);
	
	String PATH = "/user";
	
	String PATH_BUILD_ONE = PATH_IDENTIFIER+__SLASH__+"build";
	String PATH_GET_FILE = PATH_IDENTIFIER+__SLASH__+"file"+__SLASH__+"{type}";
	String PATH_DOWNLOAD_ONE = PATH_IDENTIFIER+__SLASH__+ConstantString.DOWNLOAD;
}
