package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.TitleBusiness;
import org.cyk.system.sibua.server.persistence.api.TitlePersistence;
import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class TitleBusinessImpl extends AbstractBusinessEntityImpl<Title, TitlePersistence> implements TitleBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
