package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.CivilityRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.CivilityDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class CivilityRepresentationImpl extends AbstractRepresentationEntityImpl<CivilityDto> implements CivilityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
