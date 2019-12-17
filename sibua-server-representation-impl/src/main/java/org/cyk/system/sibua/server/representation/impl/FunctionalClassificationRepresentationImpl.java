package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.FunctionalClassificationRepresentation;
import org.cyk.system.sibua.server.representation.entities.FunctionalClassificationDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class FunctionalClassificationRepresentationImpl extends AbstractRepresentationEntityImpl<FunctionalClassificationDto> implements FunctionalClassificationRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
