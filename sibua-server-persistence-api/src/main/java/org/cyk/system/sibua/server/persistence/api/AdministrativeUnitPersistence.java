package org.cyk.system.sibua.server.persistence.api;

import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface AdministrativeUnitPersistence extends PersistenceEntity<AdministrativeUnit> {

	Integer readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode,Properties properties);
	
	default Integer readMaxOrderNumberByServiceGroupByFunctionalClassification(ServiceGroup serviceGroup,FunctionalClassification functionalClassification,Properties properties) {
		if(serviceGroup == null || functionalClassification == null)
			return null;
		return readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup.getCode(), functionalClassification.getCode(), properties);
	}
	
	default Integer readMaxOrderNumberByServiceGroupByFunctionalClassification(ServiceGroup serviceGroup,FunctionalClassification functionalClassification) {
		if(serviceGroup == null || functionalClassification == null)
			return null;
		return readMaxOrderNumberByServiceGroupByFunctionalClassification(serviceGroup, functionalClassification, null);
	}
	
	/**/
	
	Integer readMaxOrderNumberByServiceGroupCode(String serviceGroupCode,Properties properties);
	
	default Integer readMaxOrderNumberByServiceGroup(ServiceGroup serviceGroup,Properties properties) {
		if(serviceGroup == null)
			return null;
		return readMaxOrderNumberByServiceGroupCode(serviceGroup.getCode(), properties);
	}
	
	default Integer readMaxOrderNumberByServiceGroup(ServiceGroup serviceGroup) {
		if(serviceGroup == null)
			return null;
		return readMaxOrderNumberByServiceGroup(serviceGroup, null);
	}
	
	/**/
	
	Collection<AdministrativeUnit> readByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode,Properties properties);
	
	default Collection<AdministrativeUnit> readByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode) {
		if(StringHelper.isBlank(serviceGroupCode) || StringHelper.isBlank(functionalClassificationCode))
			return null;
		return readByServiceGroupCodeByFunctionalClassificationCode(serviceGroupCode, functionalClassificationCode,null);
	}
	
	default Collection<AdministrativeUnit> readByServiceGroupByFunctionalClassification(ServiceGroup serviceGroup,FunctionalClassification functionalClassification,Properties properties) {
		if(serviceGroup == null || functionalClassification == null)
			return null;
		return readByServiceGroupCodeByFunctionalClassificationCode(serviceGroup.getCode(), functionalClassification.getCode(),properties);
	}
	
	default Collection<AdministrativeUnit> readByServiceGroupByFunctionalClassification(ServiceGroup serviceGroup,FunctionalClassification functionalClassification) {
		if(serviceGroup == null || functionalClassification == null)
			return null;
		return readByServiceGroupByFunctionalClassification(serviceGroup, functionalClassification,null);
	}
	
	/**/
	
	String READ_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFilters");
	String COUNT_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFilters");
	
	String READ_BY_FILTERS_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFiltersLike");
	String COUNT_BY_FILTERS_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFiltersLike");
	
	String READ_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFiltersCodesLike");
	String COUNT_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFiltersCodesLike");
	
	String READ_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readWhereCodeNotInByFilters");
	String COUNT_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countWhereCodeNotInByFilters");
	
	String READ_WHERE_CODE_NOT_IN_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readWhereCodeNotInByFiltersCodesLike");
	String COUNT_WHERE_CODE_NOT_IN_BY_FILTERS_CODES_LIKE = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countWhereCodeNotInByFiltersCodesLike");
	
	String READ_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readBySectionsCodes");
	String COUNT_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countBySectionsCodes");
	
	String READ_CHILDREN_BY_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readChildrenByCodes");
	String COUNT_CHILDREN_BY_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countChildrenByCodes");
	
	String READ_WHERE_CODE_OR_NAME_CONTAINS_AND_SECTION_CODE_LIKES = PersistenceHelper.getQueryIdentifier(AdministrativeUnit.class,"readWhereCodeOrNameContainsAndSectionCodeLikes");
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS_AND_SECTION_CODE_LIKES = PersistenceHelper.getQueryIdentifier(AdministrativeUnit.class,"countWhereCodeOrNameContainsAndSectionCodeLikes");
}
