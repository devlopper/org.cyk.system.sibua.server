package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitActivityRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitActivityDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitActivityRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitActivityDto> implements AdministrativeUnitActivityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
