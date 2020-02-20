package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.Function;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface FunctionPersistence extends PersistenceEntity<Function> {

	String READ_BY_ADMINISTRATIVE_UNITS_CODES = QueryIdentifierBuilder.getInstance().build(Function.class,"readByAdministrativeUnitsCodes");
	
	String READ_WHERE_BUSINESS_IDENTIFIER_OR_NAME_CONTAINS_BY_TYPES_CATEGORIES_CODES = QueryIdentifierBuilder.getInstance().build(Function.class,"readWhereBusinessIdentifierOrNameContainsByTypesCategoriesCodes");
	String COUNT_WHERE_BUSINESS_IDENTIFIER_OR_NAME_CONTAINS_BY_TYPES_CATEGORIES_CODES = QueryIdentifierBuilder.getInstance().build(Function.class,"countWhereBusinessIdentifierOrNameContainsByTypesCategoriesCodes");
}
