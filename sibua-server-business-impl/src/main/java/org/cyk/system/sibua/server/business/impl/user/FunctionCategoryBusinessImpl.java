package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.FunctionCategoryBusiness;
import org.cyk.system.sibua.server.persistence.api.user.FunctionCategoryPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionCategory;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class FunctionCategoryBusinessImpl extends AbstractBusinessEntityImpl<FunctionCategory, FunctionCategoryPersistence> implements FunctionCategoryBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
