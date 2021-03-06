package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.TitlePersistence;
import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class TitlePersistenceImpl extends AbstractPersistenceEntityImpl<Title> implements TitlePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}