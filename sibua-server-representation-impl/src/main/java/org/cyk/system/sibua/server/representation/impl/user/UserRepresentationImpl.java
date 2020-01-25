package org.cyk.system.sibua.server.representation.impl.user;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.cyk.system.sibua.server.business.api.user.UserBusiness;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.representation.api.user.UserRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserDto;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserRepresentationImpl extends AbstractRepresentationEntityImpl<UserDto> implements UserRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response buildIdentificationSheetsReportByIdentifier(String identifier, String isInline) {
		User user = __inject__(UserBusiness.class).findBySystemIdentifier(identifier);
		if(user == null)
			return Response.status(Status.NOT_FOUND).build();
		ByteArrayOutputStream byteArrayOutputStream = __inject__(UserBusiness.class).buildIdentificationSheetsReportByIdentifiers(identifier);
		if(byteArrayOutputStream == null)
			return Response.status(Status.NOT_FOUND).build();
		byte[] bytes = byteArrayOutputStream.toByteArray();
		ResponseBuilder response = Response.ok(bytes);
	    response.header(HttpHeaders.CONTENT_TYPE, "application/pdf");
	    if(StringHelper.isBlank(isInline))
	    	isInline = Boolean.TRUE.toString();
	    response.header(HttpHeaders.CONTENT_DISPOSITION, (Boolean.parseBoolean(isInline) ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME
	    		+"=fiche_identification_"+System.currentTimeMillis()+".pdf");
	    response.header(HttpHeaders.CONTENT_LENGTH, bytes.length);
	    return response.build();
	}
	
}
