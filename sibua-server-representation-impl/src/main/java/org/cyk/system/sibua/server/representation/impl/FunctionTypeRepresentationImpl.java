package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.FunctionTypeRepresentation;
import org.cyk.system.sibua.server.representation.entities.FunctionTypeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class FunctionTypeRepresentationImpl extends AbstractRepresentationEntityImpl<FunctionTypeDto> implements FunctionTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
