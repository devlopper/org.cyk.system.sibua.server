package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface FunctionalClassificationPersistence extends PersistenceEntity<FunctionalClassification> {

	String READ_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(FunctionalClassification.class,FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS);
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(FunctionalClassification.class,FIELD_NAME_COUNT_WHERE_CODE_OR_NAME_CONTAINS);
	
}
