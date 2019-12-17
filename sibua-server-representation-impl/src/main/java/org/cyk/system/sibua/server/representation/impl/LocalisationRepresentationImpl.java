package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.LocalisationRepresentation;
import org.cyk.system.sibua.server.representation.entities.LocalisationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class LocalisationRepresentationImpl extends AbstractRepresentationEntityImpl<LocalisationDto> implements LocalisationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
