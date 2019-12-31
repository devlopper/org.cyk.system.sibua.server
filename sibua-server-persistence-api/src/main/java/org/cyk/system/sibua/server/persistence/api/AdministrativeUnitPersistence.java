package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface AdministrativeUnitPersistence extends PersistenceEntity<AdministrativeUnit> {

	Integer readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode,Properties properties);
	
	/**/
	
	String READ_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFilters");
	String COUNT_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFilters");
	
	String READ_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readWhereCodeNotInByFilters");
	String COUNT_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countWhereCodeNotInByFilters");
	
	/*
	String READ_BY_FILTERS_01 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFilters01");
	String COUNT_BY_FILTERS_01 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFilters01");
	*/
	String READ_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readBySectionsCodes");
	String COUNT_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countBySectionsCodes");
	
}
