package org.cyk.system.sibua.server.persistence.api;

import org.cyk.system.sibua.server.persistence.entities.ActivityCostUnit;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface ActivityCostUnitPersistence extends PersistenceEntity<ActivityCostUnit> {

	String READ_BY_ACTIVITIES_CODES = QueryIdentifierBuilder.getInstance().build(ActivityCostUnit.class, "readByActivitiesCodes");
	String COUNT_BY_ACTIVITIES_CODES = QueryIdentifierBuilder.getInstance().build(ActivityCostUnit.class, "countByActivitiesCodes");
	
}