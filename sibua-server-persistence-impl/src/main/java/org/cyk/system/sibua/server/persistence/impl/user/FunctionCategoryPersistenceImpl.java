package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.FunctionCategoryPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionCategory;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionCategoryPersistenceImpl extends AbstractPersistenceEntityImpl<FunctionCategory> implements FunctionCategoryPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}