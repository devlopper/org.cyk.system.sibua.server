package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserFileRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserFileDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserFileRepresentationImpl extends AbstractRepresentationEntityImpl<UserFileDto> implements UserFileRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
