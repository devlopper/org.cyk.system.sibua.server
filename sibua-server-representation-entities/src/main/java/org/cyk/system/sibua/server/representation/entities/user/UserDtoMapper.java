package org.cyk.system.sibua.server.representation.entities.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserDtoMapper extends AbstractMapperSourceDestinationImpl<UserDto, User> {
	private static final long serialVersionUID = 1L;

	@Override
	public UserDto getSource(User user, Properties properties) {
		UserDto userDto =  super.getSource(user, properties);
		@SuppressWarnings("unchecked")
		List<String> fields = (List<String>) Properties.getFromPath(properties,Properties.FIELDS);
		if(StringHelper.isBlank(user.getReportUniformResourceIdentifier()) && (fields == null || fields.contains(User.FIELD_REPORT_UNIFORM_RESOURCE_IDENTIFIER))) {
			HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
			UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
			parameter.setRequest(request);
			parameter.setPath(new PathAsFunctionParameter());
			//TODO get /sibua/server/api better
			parameter.getPath().setValue("/sibua/server/api/"+user.getIdentifier()+"/build");
			userDto.setReportUniformResourceIdentifier(UniformResourceIdentifierHelper.build(parameter));				
		}
		return userDto;
	}
	
}