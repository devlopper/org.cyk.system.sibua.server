package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface ServiceGroupPersistence extends PersistenceEntity<ServiceGroup> {

	String READ_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(ServiceGroup.class,FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS);
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(ServiceGroup.class,FIELD_NAME_COUNT_WHERE_CODE_OR_NAME_CONTAINS);
	
}
