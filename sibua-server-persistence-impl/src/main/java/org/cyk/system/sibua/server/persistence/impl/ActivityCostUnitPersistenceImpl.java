package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityCostUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.ActivityCostUnit;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class ActivityCostUnitPersistenceImpl extends AbstractPersistenceEntityImpl<ActivityCostUnit> implements ActivityCostUnitPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}