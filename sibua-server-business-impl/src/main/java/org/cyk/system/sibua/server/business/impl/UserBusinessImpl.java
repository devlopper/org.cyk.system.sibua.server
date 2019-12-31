package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.UserBusiness;
import org.cyk.system.sibua.server.persistence.api.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.User;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserBusinessImpl extends AbstractBusinessEntityImpl<User, UserPersistence> implements UserBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
