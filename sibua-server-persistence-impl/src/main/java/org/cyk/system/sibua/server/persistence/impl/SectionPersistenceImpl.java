package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class SectionPersistenceImpl extends AbstractPersistenceEntityImpl<Section> implements SectionPersistence,Serializable {
	private static final long serialVersionUID = 1L;

}