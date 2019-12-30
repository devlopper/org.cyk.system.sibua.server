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
	
	String READ_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readBySectionsCodes");
	String COUNT_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countBySectionsCodes");
}
