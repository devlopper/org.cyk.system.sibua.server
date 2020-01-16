package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.CivilityBusiness;
import org.cyk.system.sibua.server.persistence.api.user.CivilityPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.Civility;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class CivilityBusinessImpl extends AbstractBusinessEntityImpl<Civility, CivilityPersistence> implements CivilityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
