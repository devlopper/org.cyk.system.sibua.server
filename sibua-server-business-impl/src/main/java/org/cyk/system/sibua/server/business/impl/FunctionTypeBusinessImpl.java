package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.FunctionTypeBusiness;
import org.cyk.system.sibua.server.persistence.api.FunctionTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.FunctionType;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class FunctionTypeBusinessImpl extends AbstractBusinessEntityImpl<FunctionType, FunctionTypePersistence> implements FunctionTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
