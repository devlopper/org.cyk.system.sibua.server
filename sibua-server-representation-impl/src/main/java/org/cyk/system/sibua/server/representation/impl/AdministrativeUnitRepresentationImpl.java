package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.representation.api.AdministrativeUnitRepresentation;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitDto;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class AdministrativeUnitRepresentationImpl extends AbstractRepresentationEntityImpl<AdministrativeUnitDto> implements AdministrativeUnitRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response generateCodesBySectionsCodes(List<String> sectionsCodes) {
		if(CollectionHelper.isNotEmpty(sectionsCodes))
			__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes(sectionsCodes);
		return Response.ok().build();
	}
	
}
