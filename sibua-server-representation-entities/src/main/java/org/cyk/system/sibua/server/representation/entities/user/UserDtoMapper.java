package org.cyk.system.sibua.server.representation.entities.user;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.persistence.api.query.ReadUserFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserDtoMapper extends AbstractMapperSourceDestinationImpl<UserDto, User> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public UserDto getSource(User user, Properties properties) {
		UserDto userDto =  super.getSource(user, properties);
		List<String> fields = null;
		if(Properties.getFromPath(properties,Properties.FIELDS) instanceof List)
			fields = (List<String>) Properties.getFromPath(properties,Properties.FIELDS);
		else if(Properties.getFromPath(properties,Properties.FIELDS) instanceof Strings)
			fields = (List<String>) ((Strings)Properties.getFromPath(properties,Properties.FIELDS)).get();
		else
			fields = CollectionHelper.listOf(StringUtils.split((String)Properties.getFromPath(properties,Properties.FIELDS),","));
		
		if(CollectionHelper.isEmpty(fields)) {
			if(CollectionHelper.isNotEmpty(user.getUserFiles())) {
				for(UserFileDto userFile : userDto.getUserFiles()) {
					userFile.setUser(null);
					userFile.getFile().setBytes(null);
				}
			}
		}
		
		if(StringHelper.isBlank(user.getReportUniformResourceIdentifier()) && (fields == null || fields.contains(User.FIELD_REPORT_UNIFORM_RESOURCE_IDENTIFIER))) {
			HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
			UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
			parameter.setRequest(request);
			parameter.setPath(new PathAsFunctionParameter());
			//TODO get /sibua/server/api better
			parameter.getPath().setValue("/sibua/server/api/user/"+user.getIdentifier()+"/build");
			userDto.setReportUniformResourceIdentifier(UniformResourceIdentifierHelper.build(parameter));				
		}
		
		Collection<UserFile> userFiles = ((ReadUserFileByUsers)DependencyInjection.inject(UserFilePersistence.class)).readByUsers(user);
		
		if(CollectionHelper.isNotEmpty(userFiles) && StringHelper.isBlank(user.getAdministrativeCertificateUniformResourceIdentifier()) && (fields == null || fields.contains(User.FIELD_ADMINISTRATIVE_CERTIFICATE_UNIFORM_RESOURCE_IDENTIFIER))) {
			HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
			UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
			parameter.setRequest(request);
			parameter.setPath(new PathAsFunctionParameter());
			//TODO get /sibua/server/api better
			parameter.getPath().setValue("/sibua/server/api/user/"+user.getIdentifier()+"/file/"+UserFileType.ADMINISTRATIVE_CERTIFICATE.name().toLowerCase());
			userDto.setAdministrativeCertificateUniformResourceIdentifier(UniformResourceIdentifierHelper.build(parameter));				
		}
		
		if(user.getCreationDate() != null)
			userDto.setCreationDate(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.FRENCH).format(user.getCreationDate()));
		if(user.getSendingDate() != null)
			userDto.setSendingDate(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.FRENCH).format(user.getSendingDate()));
		
		return userDto;
	}
	
}