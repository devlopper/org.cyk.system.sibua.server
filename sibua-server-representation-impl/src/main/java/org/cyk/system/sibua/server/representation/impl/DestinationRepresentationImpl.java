package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.DestinationRepresentation;
import org.cyk.system.sibua.server.representation.entities.DestinationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class DestinationRepresentationImpl extends AbstractRepresentationEntityImpl<DestinationDto> implements DestinationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
