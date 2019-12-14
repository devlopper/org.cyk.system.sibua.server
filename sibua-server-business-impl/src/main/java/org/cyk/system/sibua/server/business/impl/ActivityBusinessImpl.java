package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ActivityBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ActivityBusinessImpl extends AbstractBusinessEntityImpl<Activity, ActivityPersistence> implements ActivityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
