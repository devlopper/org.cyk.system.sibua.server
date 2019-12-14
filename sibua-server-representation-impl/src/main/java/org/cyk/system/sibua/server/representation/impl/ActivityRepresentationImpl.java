package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ActivityRepresentation;
import org.cyk.system.sibua.server.representation.entities.ActivityDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ActivityRepresentationImpl extends AbstractRepresentationEntityImpl<ActivityDto> implements ActivityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
