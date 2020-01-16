package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserSectionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserSectionPersistenceImpl extends AbstractPersistenceEntityImpl<UserSection> implements UserSectionPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}