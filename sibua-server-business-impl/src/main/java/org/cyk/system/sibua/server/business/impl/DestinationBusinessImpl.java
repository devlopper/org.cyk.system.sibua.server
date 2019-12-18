package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.DestinationBusiness;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class DestinationBusinessImpl extends AbstractBusinessEntityImpl<Destination, DestinationPersistence> implements DestinationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}