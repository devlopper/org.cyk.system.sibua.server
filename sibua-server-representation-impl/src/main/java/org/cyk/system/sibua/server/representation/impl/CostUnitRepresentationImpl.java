package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.CostUnitRepresentation;
import org.cyk.system.sibua.server.representation.entities.CostUnitDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class CostUnitRepresentationImpl extends AbstractRepresentationEntityImpl<CostUnitDto> implements CostUnitRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
