package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.AdministrativeUnitHierarchyRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitHierarchyDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitHierarchyRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitHierarchyDto> implements AdministrativeUnitHierarchyRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
