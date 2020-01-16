package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserLocalisationRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserLocalisationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserLocalisationRepresentationImpl extends AbstractRepresentationEntityImpl<UserLocalisationDto> implements UserLocalisationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
