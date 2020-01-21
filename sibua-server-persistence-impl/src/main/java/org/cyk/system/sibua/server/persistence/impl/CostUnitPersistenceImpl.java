package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.CostUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.CostUnit;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class CostUnitPersistenceImpl extends AbstractPersistenceEntityImpl<CostUnit> implements CostUnitPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}