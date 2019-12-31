package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.FunctionTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.FunctionType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionTypePersistenceImpl extends AbstractPersistenceEntityImpl<FunctionType> implements FunctionTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}