package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.CostUnitBusiness;
import org.cyk.system.sibua.server.persistence.api.CostUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.CostUnit;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class CostUnitBusinessImpl extends AbstractBusinessEntityImpl<CostUnit, CostUnitPersistence> implements CostUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
