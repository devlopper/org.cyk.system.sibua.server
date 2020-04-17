package org.cyk.system.sibua.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.annotation.System;
import org.cyk.system.sibua.server.persistence.api.ActivitySelectQuerier;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.AdministrativeUnitReadingQuerier;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
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
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.klass.PersistableClassesGetter;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.EntityReader;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryGetter;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Boolean USER_ENABLED = Boolean.TRUE;
	
	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.server.persistence.impl.ApplicationScopeLifeCycleListener.class).initialize(null);
		DependencyInjection.setQualifierClassTo(System.class, EntityReader.class,EntityCounter.class);
		QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_READ_VIEW_01
				,Query.FIELD_TUPLE_CLASS,AdministrativeUnit.class,Query.FIELD_RESULT_CLASS,AdministrativeUnit.class
				,Query.FIELD_VALUE,AdministrativeUnitReadingQuerier.QUERY_VALUE_READ_VIEW_01
				));		
		QueryHelper.addQueries(Query.buildCountFromSelect(QueryGetter.getInstance().get(AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_READ_VIEW_01)));
		
		QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_READ_VIEW_02
				,Query.FIELD_TUPLE_CLASS,AdministrativeUnit.class,Query.FIELD_RESULT_CLASS,AdministrativeUnit.class
				,Query.FIELD_VALUE,AdministrativeUnitReadingQuerier.QUERY_VALUE_READ_VIEW_02
				).setTupleFieldsNamesIndexes(Map.of("identifier",0,"asString",1,"sectionAsString",2,"serviceGroupAsString",3,"functionalClassificationAsString"
						,4,"localisationAsString",5))
				);				
		QueryHelper.addQueries(Query.buildCount(AdministrativeUnit.class,AdministrativeUnitReadingQuerier.QUERY_NAME_COUNT_VIEW_02,AdministrativeUnitReadingQuerier.QUERY_VALUE_COUNT_VIEW_02));
		
		QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,ActivitySelectQuerier.QUERY_IDENTIFIER_READ_VIEW_01
				,Query.FIELD_TUPLE_CLASS,Activity.class,Query.FIELD_RESULT_CLASS,Activity.class
				,Query.FIELD_VALUE,ActivitySelectQuerier.QUERY_VALUE_READ_VIEW_01
				).setTupleFieldsNamesIndexes(Map.of("identifier",0,"asString",1,"actionAsString",2,"programAsString",3,"sectionAsString",4,"functionTypeAsString",5
						,Activity.FIELD_BENEFICIARY_AS_STRING,6,Activity.FIELD_MANAGER_AS_STRING,7,"catAtvCode",8))
			);				
		QueryHelper.addQueries(Query.buildCount(Activity.class,ActivitySelectQuerier.QUERY_NAME_COUNT_VIEW_01,ActivitySelectQuerier.QUERY_VALUE_COUNT_VIEW_01));
		
		QueryHelper.scan(List.of(AdministrativeUnitPersistence.class.getPackage()));	
		ArrayList<Class<?>> classes = new ArrayList<>();
		if(isUserEnabled()) {
			classes.addAll(List.of(UserFunction.class,User.class,Function.class,FunctionType.class,FunctionCategory.class));
		}
		classes.addAll(List.of(AdministrativeUnitDestination.class
				,ActivityDestination.class,Destination.class/*,AdministrativeUnitHierarchy.class*/
				,AdministrativeUnitActivity.class,AdministrativeUnit.class
				,Activity.class,Action.class,Program.class,Section.class,ServiceGroup.class,Localisation.class
				,FunctionalClassification.class,Title.class));
		PersistableClassesGetter.COLLECTION.set(classes);
		__inject__(org.cyk.utility.server.persistence.impl.ApplicationScopeLifeCycleListener.class).initialize(null);
		__inject__(org.cyk.system.sibua.server.persistence.entities.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	public static Boolean isUserEnabled() {
		return Boolean.TRUE.equals(USER_ENABLED);
	}
}