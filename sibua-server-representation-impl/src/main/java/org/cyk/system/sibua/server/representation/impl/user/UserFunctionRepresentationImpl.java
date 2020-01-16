package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserFunctionRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserFunctionDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserFunctionRepresentationImpl extends AbstractRepresentationEntityImpl<UserFunctionDto> implements UserFunctionRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
