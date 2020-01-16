package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.CivilityPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.Civility;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class CivilityPersistenceImpl extends AbstractPersistenceEntityImpl<Civility> implements CivilityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}