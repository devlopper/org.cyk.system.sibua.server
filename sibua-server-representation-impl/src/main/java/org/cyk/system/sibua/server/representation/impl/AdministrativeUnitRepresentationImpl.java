package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitDto> implements AdministrativeUnitRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
