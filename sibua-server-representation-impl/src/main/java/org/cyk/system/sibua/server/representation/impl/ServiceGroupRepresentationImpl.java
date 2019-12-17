package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.ServiceGroupRepresentation;
import org.cyk.system.sibua.server.representation.entities.ServiceGroupDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ServiceGroupRepresentationImpl extends AbstractRepresentationEntityImpl<ServiceGroupDto> implements ServiceGroupRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
