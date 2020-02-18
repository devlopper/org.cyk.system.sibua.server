package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserProgramRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserProgramDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserProgramRepresentationImpl extends AbstractRepresentationEntityImpl<UserProgramDto> implements UserProgramRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
