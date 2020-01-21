package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ActivityCostUnitRepresentation;
import org.cyk.system.sibua.server.representation.entities.ActivityCostUnitDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ActivityCostUnitRepresentationImpl extends AbstractRepresentationEntityImpl<ActivityCostUnitDto> implements ActivityCostUnitRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
