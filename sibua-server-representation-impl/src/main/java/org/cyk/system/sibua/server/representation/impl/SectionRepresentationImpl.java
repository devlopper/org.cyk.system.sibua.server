package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.SectionRepresentation;
import org.cyk.system.sibua.server.representation.entities.SectionDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class SectionRepresentationImpl extends AbstractRepresentationEntityImpl<SectionDto> implements SectionRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
