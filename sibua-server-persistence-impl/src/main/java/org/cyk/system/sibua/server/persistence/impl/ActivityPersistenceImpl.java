package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByActions;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityBySections;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class ActivityPersistenceImpl extends AbstractPersistenceEntityImpl<Activity> implements ActivityPersistence,ReadActivityBySections,ReadActivityByPrograms,ReadActivityByActions,ReadActivityByAdministrativeUnits,Serializable {
	private static final long serialVersionUID = 1L;

	private String readWhereAdministrativeUnitDoesNotExistBySectionsCodes,readWhereAdministrativeUnitDoesNotExistByProgramsCodes
	,readWhereAdministrativeUnitDoesNotExistByActionsCodes,readBySectionsCodes,readByProgramsCodes,readByActionsCodes,readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readBySectionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.section.code IN :sectionsCodes");
		addQueryCollectInstances(readByProgramsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes");
		addQueryCollectInstances(readByActionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.code IN :actionsCodes");
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT activity FROM Activity activity WHERE EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity AND administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes)");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistBySectionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.section.code IN :sectionsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistByProgramsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistByActionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.code IN :actionsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)");
	}
	
	@Override
	public Collection<Activity> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes,Properties properties) {
		if(CollectionHelper.isEmpty(sectionsCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readWhereAdministrativeUnitDoesNotExistBySectionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,sectionsCodes));
	}
	
	@Override
	public Collection<Activity> readWhereAdministrativeUnitDoesNotExistByProgramsCodes(Collection<String> programsCodes,Properties properties) {
		if(CollectionHelper.isEmpty(programsCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readWhereAdministrativeUnitDoesNotExistByProgramsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,programsCodes));
	}
	
	@Override
	public Collection<Activity> readBySectionsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readBySectionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<Activity> readByProgramsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByProgramsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<Activity> readByActionsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByActionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<Activity> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Activity.FIELD_SECTION)))
				return readBySectionsCodes;
		}
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Activity.FIELD_PROGRAM)))
				return readByProgramsCodes;
		}
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Activity.FIELD_ACTION)))
				return readByActionsCodes;
		}
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Activity.FIELD_ADMINISTRATIVE_UNIT)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereAdministrativeUnitDoesNotExistBySectionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_SECTION)};
			return new Object[]{"sectionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereAdministrativeUnitDoesNotExistByProgramsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_PROGRAM)};
			return new Object[]{"programsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereAdministrativeUnitDoesNotExistByActionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_ACTION)};
			return new Object[]{"actionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySectionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_SECTION)};
			return new Object[]{"sectionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByProgramsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_PROGRAM)};
			return new Object[]{"programsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_ACTION)};
			return new Object[]{"actionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}