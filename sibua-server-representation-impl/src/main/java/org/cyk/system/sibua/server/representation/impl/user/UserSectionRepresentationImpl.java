package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserSectionRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserSectionDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserSectionRepresentationImpl extends AbstractRepresentationEntityImpl<UserSectionDto> implements UserSectionRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
