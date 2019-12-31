package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.FunctionBusiness;
import org.cyk.system.sibua.server.persistence.api.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Function;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class FunctionBusinessImpl extends AbstractBusinessEntityImpl<Function, FunctionPersistence> implements FunctionBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
