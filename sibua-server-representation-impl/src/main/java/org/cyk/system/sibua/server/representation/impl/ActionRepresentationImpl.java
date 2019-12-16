package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ActionRepresentation;
import org.cyk.system.sibua.server.representation.entities.ActionDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ActionRepresentationImpl extends AbstractRepresentationEntityImpl<ActionDto> implements ActionRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
