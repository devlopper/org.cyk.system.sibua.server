package org.cyk.system.sibua.server.representation.impl.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.cyk.system.sibua.server.business.api.user.UserBusiness;
import org.cyk.system.sibua.server.persistence.api.query.ReadUserFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.representation.api.user.UserRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserDto;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.file.FileHelper;
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

	@Override
	public Response getFileByIdentifier(String identifier, String type, String isInline) {
		User user = __inject__(UserBusiness.class).findBySystemIdentifier(identifier);
		if(user == null)
			return Response.status(Status.NOT_FOUND).build();
		Collection<UserFile> userFiles = ((ReadUserFileByUsers)__inject__(UserFilePersistence.class)).readByUsers(user);
		if(CollectionHelper.isEmpty(userFiles))
			return Response.status(Status.NOT_FOUND).build();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		UserFile userFile = CollectionHelper.getFirst(userFiles);
		try {
			byteArrayOutputStream.write(userFile.getFile().getBytes());
		} catch (IOException exception) {
			return Response.serverError().entity(exception).build();
		}
		byte[] bytes = byteArrayOutputStream.toByteArray();
		ResponseBuilder response = Response.ok(bytes);
	    response.header(HttpHeaders.CONTENT_TYPE, FileHelper.getMimeTypeByExtension(userFile.getFile().getExtension()));
	    if(StringHelper.isBlank(isInline))
	    	isInline = Boolean.TRUE.toString();
	    response.header(HttpHeaders.CONTENT_DISPOSITION, (Boolean.parseBoolean(isInline) ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME
	    		+"=acte_nomination_."+userFile.getFile().getExtension());
	    response.header(HttpHeaders.CONTENT_LENGTH, bytes.length);
	    return response.build();
	}

	@Override
	public Response notifyAccessToken(List<String> usersIdentifiers) {
		if(CollectionHelper.isNotEmpty(usersIdentifiers))
			__inject__(UserBusiness.class).notifyAccessTokenByIdentifiers(usersIdentifiers);
		return Response.ok().build();
	}

	@Override
	public Response notifyAccessTokenByElectronicMailAddresses(List<String> electronicMailAddresses) {
		if(CollectionHelper.isEmpty(electronicMailAddresses))
			return Response.ok("No mail sent. Empty electronic mail addresses").build();
		__inject__(UserBusiness.class).notifyAccessTokenByElectronicMailAddresses(electronicMailAddresses);
		return Response.ok().build();
	}
	
}
