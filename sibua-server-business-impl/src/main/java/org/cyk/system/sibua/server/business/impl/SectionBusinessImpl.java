package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class SectionBusinessImpl extends AbstractBusinessEntityImpl<Section, SectionPersistence> implements SectionBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
