package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserTypeRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserTypeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserTypeRepresentationImpl extends AbstractRepresentationEntityImpl<UserTypeDto> implements UserTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
