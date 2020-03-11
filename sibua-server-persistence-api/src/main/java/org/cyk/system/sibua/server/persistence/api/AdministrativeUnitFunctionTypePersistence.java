package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFunctionType;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface AdministrativeUnitFunctionTypePersistence extends PersistenceEntity<AdministrativeUnitFunctionType> {

	String READ_BY_ADMINISTRATIVE_UNITS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnitFunctionType.class, "readByAdministrativeUnitsCodes");
	
	String COUNT_BY_ADMINISTRATIVE_UNITS_CODES = QueryIdentifierBuilder.getInstance().build(AdministrativeUnitFunctionType.class, "countByAdministrativeUnitsCodes");
	
}