package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.UserAdministrativeUnitRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.UserAdministrativeUnitDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class UserAdministrativeUnitRepresentationImpl extends AbstractRepresentationEntityImpl<UserAdministrativeUnitDto> implements UserAdministrativeUnitRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
