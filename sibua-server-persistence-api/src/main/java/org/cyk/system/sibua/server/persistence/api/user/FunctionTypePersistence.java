package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface FunctionTypePersistence extends PersistenceEntity<FunctionType> {

	String READ_BY_ADMINISTRATIVE_UNITS_CODES = QueryIdentifierBuilder.getInstance().build(FunctionType.class,"readByAdministrativeUnitsCodes");
	String COUNT_BY_ADMINISTRATIVE_UNITS_CODES = QueryIdentifierBuilder.getInstance().build(FunctionType.class,"countByAdministrativeUnitsCodes");
	
}
