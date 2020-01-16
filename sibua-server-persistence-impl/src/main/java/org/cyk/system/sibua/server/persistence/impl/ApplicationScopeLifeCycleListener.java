package org.cyk.system.sibua.server.persistence.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.system.sibua.server.persistence.entities.user.Function;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionCategory;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.system.sibua.server.persistence.entities.user.UserAdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.klass.PersistableClassesGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		PersistableClassesGetter.COLLECTION.set(List.of(AdministrativeUnitDestination.class
				,UserFunction.class,UserLocalisation.class,UserSection.class,UserActivity.class,UserFile.class,UserAdministrativeUnit.class
				,ActivityDestination.class,Destination.class,AdministrativeUnitHierarchy.class
				,AdministrativeUnitActivity.class,AdministrativeUnit.class,Activity.class,Action.class,Program.class,Section.class,ServiceGroup.class,Localisation.class
				,FunctionalClassification.class,Title.class,User.class,Function.class,FunctionType.class,FunctionCategory.class));
		__inject__(org.cyk.utility.server.persistence.impl.ApplicationScopeLifeCycleListener.class).initialize(null);
		__inject__(org.cyk.system.sibua.server.persistence.entities.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
