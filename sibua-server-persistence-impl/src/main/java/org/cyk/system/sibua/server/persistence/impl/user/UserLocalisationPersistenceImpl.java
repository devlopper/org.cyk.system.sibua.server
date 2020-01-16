package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserLocalisationPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserLocalisationPersistenceImpl extends AbstractPersistenceEntityImpl<UserLocalisation> implements UserLocalisationPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}