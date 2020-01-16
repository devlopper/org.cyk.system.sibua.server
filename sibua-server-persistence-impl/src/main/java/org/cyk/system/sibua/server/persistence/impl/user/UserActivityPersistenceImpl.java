package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserActivityPersistenceImpl extends AbstractPersistenceEntityImpl<UserActivity> implements UserActivityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}