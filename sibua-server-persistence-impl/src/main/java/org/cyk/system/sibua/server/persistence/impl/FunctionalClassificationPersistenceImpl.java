package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionalClassificationPersistenceImpl extends AbstractPersistenceEntityImpl<FunctionalClassification> implements FunctionalClassificationPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}