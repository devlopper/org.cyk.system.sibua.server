package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserSectionBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserSectionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserSectionBusinessImpl extends AbstractBusinessEntityImpl<UserSection, UserSectionPersistence> implements UserSectionBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
