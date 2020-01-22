package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityCostUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.ActivityCostUnit;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class ActivityCostUnitPersistenceImpl extends AbstractPersistenceEntityImpl<ActivityCostUnit> implements ActivityCostUnitPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByActivitiesCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByActivitiesCodes, "SELECT activityCostUnit FROM ActivityCostUnit activityCostUnit WHERE activityCostUnit.activity.code IN :activitiesCodes ORDER BY activityCostUnit.activity.code ASC");
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, ActivityCostUnit.FIELD_ACTIVITY)))
				return readByActivitiesCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActivitiesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(ActivityCostUnit.FIELD_ACTIVITY)};
			return new Object[]{"activitiesCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}