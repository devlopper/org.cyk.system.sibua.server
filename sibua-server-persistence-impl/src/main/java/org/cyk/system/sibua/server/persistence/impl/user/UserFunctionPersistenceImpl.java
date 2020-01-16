package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserFunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserFunctionPersistenceImpl extends AbstractPersistenceEntityImpl<UserFunction> implements UserFunctionPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}