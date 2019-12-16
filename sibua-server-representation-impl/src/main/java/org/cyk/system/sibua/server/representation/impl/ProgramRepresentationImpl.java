package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ProgramRepresentation;
import org.cyk.system.sibua.server.representation.entities.ProgramDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ProgramRepresentationImpl extends AbstractRepresentationEntityImpl<ProgramDto> implements ProgramRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
