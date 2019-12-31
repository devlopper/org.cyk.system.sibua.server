package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.UserFileRepresentation;
import org.cyk.system.sibua.server.representation.entities.UserFileDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserFileRepresentationImpl extends AbstractRepresentationEntityImpl<UserFileDto> implements UserFileRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
