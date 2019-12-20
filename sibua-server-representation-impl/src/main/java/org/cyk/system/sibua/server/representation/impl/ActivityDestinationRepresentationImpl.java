package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ActivityDestinationRepresentation;
import org.cyk.system.sibua.server.representation.entities.ActivityDestinationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ActivityDestinationRepresentationImpl extends AbstractRepresentationEntityImpl<ActivityDestinationDto> implements ActivityDestinationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
