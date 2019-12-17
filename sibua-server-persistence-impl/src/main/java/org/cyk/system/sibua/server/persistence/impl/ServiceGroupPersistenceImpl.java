package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class ServiceGroupPersistenceImpl extends AbstractPersistenceEntityImpl<ServiceGroup> implements ServiceGroupPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}