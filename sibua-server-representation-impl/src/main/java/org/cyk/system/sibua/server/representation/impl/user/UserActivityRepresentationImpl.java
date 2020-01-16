package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserActivityRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserActivityDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserActivityRepresentationImpl extends AbstractRepresentationEntityImpl<UserActivityDto> implements UserActivityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
