package org.cyk.system.sibua.server.persistence.api;

import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface ActivityPersistence extends PersistenceEntity<Activity> {

	Collection<Activity> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes,Properties properties);
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes) {
		if(CollectionHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(sectionsCodes, null);
	}
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Properties properties,String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), properties);
	}
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), null);
	}
	
	Collection<Activity> readWhereAdministrativeUnitDoesNotExistByProgramsCodes(Collection<String> programsCodes,Properties properties);
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistByProgramsCodes(Collection<String> programsCodes) {
		if(CollectionHelper.isEmpty(programsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistByProgramsCodes(programsCodes, null);
	}
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistByProgramsCodes(Properties properties,String...programsCodes){
		if(ArrayHelper.isEmpty(programsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistByProgramsCodes(CollectionHelper.listOf(programsCodes), properties);
	}
	
	default Collection<Activity> readWhereAdministrativeUnitDoesNotExistByProgramsCodes(String...programsCodes){
		if(ArrayHelper.isEmpty(programsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistByProgramsCodes(CollectionHelper.listOf(programsCodes), null);
	}
	
	/**/
	
	//Collection<Activity> readByAdministrativeUnitsCodesByAdministrativeUnitActivityTypesCodes(Collection<String> administrativeUnitsCodes
	//		,Collection<String> administrativeUnitActivityTypesCodes,Properties properties);
	
	/**/
	
	String READ_WHERE_ADMINISTRATIVE_UNIT_DOES_NOT_EXIST_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(Activity.class,"readWhereAdministrativeUnitDoesNotExistBySectionsCodes");
	String READ_WHERE_ADMINISTRATIVE_UNIT_DOES_NOT_EXIST_BY_PROGRAMS_CODES = QueryIdentifierBuilder.getInstance().build(Activity.class,"readWhereAdministrativeUnitDoesNotExistByProgramsCodes");
	String READ_WHERE_ADMINISTRATIVE_UNIT_DOES_NOT_EXIST_BY_ACTIONS_CODES = QueryIdentifierBuilder.getInstance().build(Activity.class,"readWhereAdministrativeUnitDoesNotExistByActionsCodes");
	
	String READ_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(Activity.class,"readByFilters");
	String COUNT_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(Activity.class,"countByFilters");
	
	String READ_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(Activity.class,"readByFiltersCodesLike");
	String COUNT_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(Activity.class,"countByFiltersCodesLike");
	
	String READ_WHERE_ADMINISTRATIVE_UNIT_DOES_NOT_EXIST_BY_FILTERS = PersistenceHelper.getQueryIdentifier(Activity.class,"readWhereAdministrativeUnitDoesNotExistByFilters");
	
	String READ_WHERE_CODE_NOT_IN_AND_ADMINISTRATIVEUNIT_DOES_NOT_EXIST_BY_FILTERS = PersistenceHelper.getQueryIdentifier(Activity.class,"readWhereCodeNotInAndAdministrativeUnitDoesNotExistByFilters");
	String COUNT_WHERE_CODE_NOT_IN_AND_ADMINISTRATIVEUNIT_DOES_NOT_EXIST_BY_FILTERS = PersistenceHelper.getQueryIdentifier(Activity.class,"countWhereCodeNotInAndAdministrativeUnitDoesNotExistByFilters");
	
}