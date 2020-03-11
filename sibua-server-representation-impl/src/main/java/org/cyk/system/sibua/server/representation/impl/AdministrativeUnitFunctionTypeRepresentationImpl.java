package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitFunctionTypeRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitFunctionTypeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitFunctionTypeRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitFunctionTypeDto> implements AdministrativeUnitFunctionTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
