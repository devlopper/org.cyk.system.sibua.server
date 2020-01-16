package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserActivityBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserActivityBusinessImpl extends AbstractBusinessEntityImpl<UserActivity, UserActivityPersistence> implements UserActivityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
