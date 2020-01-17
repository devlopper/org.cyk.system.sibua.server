package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByActions;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadDestinationByActivities;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class ActivityPersistenceImpl extends AbstractPersistenceEntityImpl<Activity> implements ActivityPersistence,ReadActivityBySections,ReadActivityByPrograms,ReadActivityByActions,ReadActivityByAdministrativeUnits,ReadActivityByUsers,Serializable {
	private static final long serialVersionUID = 1L;

	private static final String AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7 = " AND activity.action.program.budgetCategoryCode IN ('1','3','7') ";
	
	private String readWhereAdministrativeUnitDoesNotExistBySectionsCodes,readWhereAdministrativeUnitDoesNotExistByProgramsCodes
	,readWhereAdministrativeUnitDoesNotExistByActionsCodes,readBySectionsCodes,readByProgramsCodes,readByActionsCodes,readByAdministrativeUnitsCodes
	,readByFilters,readWhereCodeNotInByFilters,readWhereAdministrativeUnitDoesNotExistByFilters,readWhereCodeNotInAndAdministrativeUnitDoesNotExistByFilters
	,readByFiltersCodesLike,readByUsersIdentifiers;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(ApplicationScopeLifeCycleListener.isUserEnabled())
			addQueryCollectInstances(readByUsersIdentifiers, "SELECT activity FROM Activity activity WHERE EXISTS (SELECT userActivity FROM UserActivity userActivity WHERE userActivity.activity = activity AND userActivity.user.identifier IN :usersIdentifiers)  ORDER BY activity.code ASC");
		addQueryCollectInstances(readBySectionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.section.code IN :sectionsCodes ORDER BY activity.code ASC");
		addQueryCollectInstances(readByProgramsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes  ORDER BY activity.code ASC");
		addQueryCollectInstances(readByActionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.code IN :actionsCodes  ORDER BY activity.code ASC");
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT activity FROM Activity activity WHERE EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity AND administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes)  ORDER BY activity.code ASC");
		/*
		addQueryCollectInstances(readByAdministrativeUnitsCodesByAdministrativeUnitActivityTypesCodes, "SELECT activity FROM Activity activity WHERE EXISTS "
				+ "(SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity "
				+ "AND administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes AND administrativeUnitActivity.type.code IN :administrativeUnitActivityTypesCodes) ORDER BY activity.code ASC");
		*/
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistBySectionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.section.code IN :sectionsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)  ORDER BY activity.code ASC");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistByProgramsCodes, "SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)  ORDER BY activity.code ASC");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistByActionsCodes, "SELECT activity FROM Activity activity WHERE activity.action.code IN :actionsCodes AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity)  ORDER BY activity.code ASC");
		addQueryCollectInstances(readByFilters, "SELECT activity FROM Activity activity "
						+ "WHERE "
						+ "LOWER(activity.code) LIKE LOWER(:code) "
						+ "AND LOWER(activity.name) LIKE LOWER(:name) "
						+ "AND activity.action.code IN :actionsCodes "
						+ "AND activity.action.program.code IN :programsCodes "
						+ "AND activity.action.program.section.code IN :sectionsCodes "
						+ "AND (:anyAdministrativeUnit = true OR EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity AND administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes)) "
						+ AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7
						//+ "AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity) "
						+ "ORDER BY activity.code ASC");
		
		addQueryCollectInstances(readByFiltersCodesLike, "SELECT activity FROM Activity activity "
				+ "WHERE "
				+ "LOWER(activity.code) LIKE LOWER(:code) "
				+ "AND LOWER(activity.name) LIKE LOWER(:name) "
				+ "AND activity.action.code LIKE LOWER(:actionCode) "
				+ "AND activity.action.program.code LIKE LOWER(:programCode) "
				+ "AND activity.action.program.section.code LIKE LOWER(:sectionCode) "
				+ "AND (:anyAdministrativeUnit = true OR EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity AND administrativeUnitActivity.administrativeUnit.code LIKE LOWER(:administrativeUnitCode))) "
				+ AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7
				//+ "AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity) "
				+ "ORDER BY activity.code ASC");
		
		addQueryCollectInstances(readWhereCodeNotInByFilters, "SELECT activity FROM Activity activity "
				+ "WHERE "
				+ "activity.code NOT IN :codes "
				+ "AND LOWER(activity.name) LIKE LOWER(:name) "
				+ "AND activity.action.program.section.code IN :sectionsCodes "
				+ "AND activity.action.program.code IN :programsCodes "
				+ "AND activity.action.code IN :actionsCodes "
				+ AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7
				//+ "AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity) "
				+ "ORDER BY activity.code ASC");
		
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistByFilters, "SELECT activity FROM Activity activity "
				+ "WHERE "
				+ "LOWER(activity.code) LIKE LOWER(:code) "
				+ "AND LOWER(activity.name) LIKE LOWER(:name) "
				+ "AND activity.action.program.section.code IN :sectionsCodes "
				+ "AND activity.action.program.code IN :programsCodes "
				+ "AND activity.action.code IN :actionsCodes "
				+ AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7
				+ "AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity) "
				+ "ORDER BY activity.code ASC");

		addQueryCollectInstances(readWhereCodeNotInAndAdministrativeUnitDoesNotExistByFilters, "SELECT activity FROM Activity activity "
				+ "WHERE "
				+ "activity.code NOT IN :codes "
				+ "AND LOWER(activity.name) LIKE LOWER(:name) "
				+ "AND activity.action.program.section.code IN :sectionsCodes "
				+ "AND activity.action.program.code IN :programsCodes "
				+ "AND activity.action.code IN :actionsCodes "
				+ AND_PROGRAM_BUDGET_CATEGORY_CODE_IS_1_OR_3_OR_7
				+ "AND NOT EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activity) "
				+ "ORDER BY activity.code ASC");
	}
	
	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(Activity activity, Field field, Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(activity, field, properties);
		if(field.getName().equals(Activity.FIELD_DESTINATIONS)) {
			activity.setDestinations(((ReadDestinationByActivities)__inject__(DestinationPersistence.class))
					.readByActivities(activity));
		}else if(field.getName().equals(Activity.FIELD_ADMINISTRATIVE_UNIT)) {
			Collection<AdministrativeUnit> administrativeUnits = ((ReadAdministrativeUnitByActivities)__inject__(AdministrativeUnitPersistence.class)).readByActivities(activity);
			if(CollectionHelper.isNotEmpty(administrativeUnits)) {
				activity.setAdministrativeUnit(CollectionHelper.getFirst(administrativeUnits));
			}
			
			if(activity.getAdministrativeUnit() != null) {
				Collection<AdministrativeUnitActivity> administrativeUnitActivities = __inject__(AdministrativeUnitActivityPersistence.class)
						.readByAdministrativeUnitsCodesByActivitiesCodes(List.of(activity.getAdministrativeUnit().getCode())
								, List.of(activity.getCode()),null);
				if(CollectionHelper.isNotEmpty(administrativeUnitActivities)) {
					activity.setAdministrativeUnitBeneficiaire(CollectionHelper.getFirst(administrativeUnitActivities).getAdministrativeUnitBeneficiaire());
				}	
			}else {
				AdministrativeUnitActivity administrativeUnitActivity = CollectionHelper.getFirst( 
						((ReadAdministrativeUnitActivityByActivities) __inject__(AdministrativeUnitActivityPersistence.class)).readByActivities(activity));
				if(administrativeUnitActivity != null)
					activity.setAdministrativeUnitBeneficiaire(administrativeUnitActivity.getAdministrativeUnitBeneficiaire());
			}
		}/*else if(field.getName().equals(Activity.FIELD_ADMINISTRATIVE_UNIT_BENEFICIAIRE)) {
			if(activity.getAdministrativeUnit() != null) {
				Collection<AdministrativeUnitActivity> administrativeUnitActivities = __inject__(AdministrativeUnitActivityPersistence.class)
						.readByAdministrativeUnitsCodesByActivitiesCodes(List.of(activity.getAdministrativeUnit().getCode())
								, List.of(activity.getCode()),null);
				if(CollectionHelper.isNotEmpty(administrativeUnitActivities)) {
					activity.setAdministrativeUnitBeneficiaire(CollectionHelper.getFirst(administrativeUnitActivities).getAdministrativeUnitBeneficiaire());
				}	
			}			
		}*/
	}
	/*
	@Override
	public Collection<Activity> readByAdministrativeUnitsCodesByAdministrativeUnitActivityTypesCodes(Collection<String> administrativeUnitsCodes
			, Collection<String> administrativeUnitActivityTypesCodes,Properties properties) {		
		if(CollectionHelper.isEmpty(administrativeUnitsCodes) || CollectionHelper.isEmpty(administrativeUnitActivityTypesCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodesByAdministrativeUnitActivityTypesCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,administrativeUnitsCodes,administrativeUnitActivityTypesCodes));
	}
	*/
	@Override
	public Collection<Activity> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
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
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
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
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		/*
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodesByAdministrativeUnitActivityTypesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Activity.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0],"administrativeUnitActivityTypesCodes",objects[1]};
		}
		*/
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFilters)
				|| queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereAdministrativeUnitDoesNotExistByFilters)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getStringLike(Activity.FIELD_CODE),queryContext.getStringLike(Activity.FIELD_NAME)
						,queryContext.getCodes(Section.class),queryContext.getCodes(Program.class),queryContext.getCodes(Action.class)};
			
			Collection<String> administrativeUnitsCodes = queryContext.getCodes(AdministrativeUnit.class, Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE, Boolean.FALSE);
			Boolean anyAdministrativeUnit;
			if(CollectionHelper.isEmpty(administrativeUnitsCodes)) {
				administrativeUnitsCodes = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				anyAdministrativeUnit = Boolean.TRUE;
			}else
				anyAdministrativeUnit = Boolean.FALSE;
			
			if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFilters))
				objects = new Object[]{Activity.FIELD_CODE,objects[0],Activity.FIELD_NAME,objects[1],"sectionsCodes",objects[2],"programsCodes",objects[3]
					,"actionsCodes",objects[4],"anyAdministrativeUnit", anyAdministrativeUnit,"administrativeUnitsCodes",administrativeUnitsCodes};
			else
				objects = new Object[]{Activity.FIELD_CODE,objects[0],Activity.FIELD_NAME,objects[1],"sectionsCodes",objects[2],"programsCodes",objects[3]
						,"actionsCodes",objects[4]};
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFiltersCodesLike)) {
			if(ArrayHelper.isEmpty(objects)) {
				objects = new Object[] {queryContext.getStringLike(Activity.FIELD_CODE),queryContext.getStringLike(Activity.FIELD_NAME)
						,queryContext.getStringLike(Activity.FIELD_SECTION),queryContext.getStringLike(Activity.FIELD_PROGRAM)
						,queryContext.getStringLike(Activity.FIELD_ACTION),queryContext.getStringLike(Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE)};
			}			
			objects = new Object[]{Activity.FIELD_CODE,objects[0],Activity.FIELD_NAME,objects[1],"sectionCode",objects[2],"programCode",objects[3],"actionCode",objects[4]
					,"anyAdministrativeUnit",Boolean.TRUE,"administrativeUnitCode",objects[5]};
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeNotInByFilters)
				|| queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeNotInAndAdministrativeUnitDoesNotExistByFilters)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getStrings(Activity.FIELD_CODE),queryContext.getStringLike(Activity.FIELD_NAME)
						,queryContext.getCodes(Section.class),queryContext.getCodes(Program.class),queryContext.getCodes(Action.class)};
			objects = new Object[]{"codes",objects[0],Activity.FIELD_NAME,objects[1],"sectionsCodes",objects[2],"programsCodes",objects[3],"actionsCodes",objects[4]};
			return objects;
		}
		
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}