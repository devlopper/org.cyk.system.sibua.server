package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.FunctionTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionTypePersistenceImpl extends AbstractPersistenceEntityImpl<FunctionType> implements FunctionTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}