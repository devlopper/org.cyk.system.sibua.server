package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserFunctionBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserFunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserFunctionBusinessImpl extends AbstractBusinessEntityImpl<UserFunction, UserFunctionPersistence> implements UserFunctionBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
