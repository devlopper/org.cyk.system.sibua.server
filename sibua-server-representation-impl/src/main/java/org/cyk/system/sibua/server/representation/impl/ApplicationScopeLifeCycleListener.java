package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.system.sibua.server.business.impl.ApplicationScopeLifeCycleListener.class).initialize(null);
		__setQualifierClassTo__(org.cyk.system.sibua.server.annotation.System.class, org.cyk.utility.server.representation.DataLoader.class);
	}
	 
	@Override
	public void __destroy__(Object object) {}
	
}