package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ServiceGroupBusiness;
import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ServiceGroupBusinessImpl extends AbstractBusinessEntityImpl<ServiceGroup, ServiceGroupPersistence> implements ServiceGroupBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
