package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface SectionPersistence extends PersistenceEntity<Section> {

	String READ_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(Section.class,FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS);
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(Section.class,FIELD_NAME_COUNT_WHERE_CODE_OR_NAME_CONTAINS);
	
}
