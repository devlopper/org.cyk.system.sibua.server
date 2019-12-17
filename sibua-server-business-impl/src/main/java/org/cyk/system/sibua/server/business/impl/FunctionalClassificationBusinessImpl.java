package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.FunctionalClassificationBusiness;
import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class FunctionalClassificationBusinessImpl extends AbstractBusinessEntityImpl<FunctionalClassification, FunctionalClassificationPersistence> implements FunctionalClassificationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
