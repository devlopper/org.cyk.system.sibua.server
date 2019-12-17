package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.LocalisationBusiness;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class LocalisationBusinessImpl extends AbstractBusinessEntityImpl<Localisation, LocalisationPersistence> implements LocalisationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
