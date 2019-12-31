package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.UserFileBusiness;
import org.cyk.system.sibua.server.persistence.api.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.UserFile;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserFileBusinessImpl extends AbstractBusinessEntityImpl<UserFile, UserFilePersistence> implements UserFileBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
