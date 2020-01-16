package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserLocalisationBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserLocalisationPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserLocalisationBusinessImpl extends AbstractBusinessEntityImpl<UserLocalisation, UserLocalisationPersistence> implements UserLocalisationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
