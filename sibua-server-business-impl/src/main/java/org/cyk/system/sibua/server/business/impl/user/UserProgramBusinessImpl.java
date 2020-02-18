package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserProgramBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserProgramPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserProgram;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserProgramBusinessImpl extends AbstractBusinessEntityImpl<UserProgram, UserProgramPersistence> implements UserProgramBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
