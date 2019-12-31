package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.UserRepresentation;
import org.cyk.system.sibua.server.representation.entities.UserDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserRepresentationImpl extends AbstractRepresentationEntityImpl<UserDto> implements UserRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
