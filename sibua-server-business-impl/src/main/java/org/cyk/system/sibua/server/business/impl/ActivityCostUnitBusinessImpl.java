package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ActivityCostUnitBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityCostUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.ActivityCostUnit;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ActivityCostUnitBusinessImpl extends AbstractBusinessEntityImpl<ActivityCostUnit, ActivityCostUnitPersistence> implements ActivityCostUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
