package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserTypeBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserType;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserTypeBusinessImpl extends AbstractBusinessEntityImpl<UserType, UserTypePersistence> implements UserTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
