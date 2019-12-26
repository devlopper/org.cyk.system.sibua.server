package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitFromDestinationRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitFromDestinationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitFromDestinationRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitFromDestinationDto> implements AdministrativeUnitFromDestinationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
