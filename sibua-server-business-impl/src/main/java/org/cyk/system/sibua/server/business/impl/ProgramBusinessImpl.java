package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ProgramBusiness;
import org.cyk.system.sibua.server.persistence.api.ProgramPersistence;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ProgramBusinessImpl extends AbstractBusinessEntityImpl<Program, ProgramPersistence> implements ProgramBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
