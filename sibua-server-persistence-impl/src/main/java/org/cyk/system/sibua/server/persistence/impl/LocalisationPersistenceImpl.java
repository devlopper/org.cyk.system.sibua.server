package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class LocalisationPersistenceImpl extends AbstractPersistenceEntityImpl<Localisation> implements LocalisationPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}