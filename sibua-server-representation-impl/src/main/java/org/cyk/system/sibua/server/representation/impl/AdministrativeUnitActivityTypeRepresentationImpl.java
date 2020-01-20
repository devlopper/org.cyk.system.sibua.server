package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitActivityTypeRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitActivityTypeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped @Deprecated
public class AdministrativeUnitActivityTypeRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitActivityTypeDto> implements AdministrativeUnitActivityTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
