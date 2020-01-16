package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserFilePersistenceImpl extends AbstractPersistenceEntityImpl<UserFile> implements UserFilePersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}