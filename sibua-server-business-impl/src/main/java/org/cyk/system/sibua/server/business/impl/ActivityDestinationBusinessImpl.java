package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ActivityDestinationBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityDestinationPersistence;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ActivityDestinationBusinessImpl extends AbstractBusinessEntityImpl<ActivityDestination, ActivityDestinationPersistence> implements ActivityDestinationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
