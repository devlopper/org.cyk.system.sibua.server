package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ActionBusiness;
import org.cyk.system.sibua.server.persistence.api.ActionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ActionBusinessImpl extends AbstractBusinessEntityImpl<Action, ActionPersistence> implements ActionBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
