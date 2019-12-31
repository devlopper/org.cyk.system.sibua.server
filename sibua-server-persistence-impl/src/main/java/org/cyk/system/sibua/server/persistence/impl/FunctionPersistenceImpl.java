package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Function;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionPersistenceImpl extends AbstractPersistenceEntityImpl<Function> implements FunctionPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}