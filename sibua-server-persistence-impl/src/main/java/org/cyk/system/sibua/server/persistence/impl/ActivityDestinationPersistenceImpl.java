package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByActivities;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class ActivityDestinationPersistenceImpl extends AbstractPersistenceEntityImpl<ActivityDestination> implements ActivityDestinationPersistence,ReadActivityDestinationByActivities,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByActivitiesCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByActivitiesCodes, "SELECT activityDestination FROM ActivityDestination activityDestination WHERE activityDestination.activity.code IN :activitiesCodes");
	}
	
	@Override
	public Collection<ActivityDestination> readByActivitiesCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByActivitiesCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, ActivityDestination.FIELD_ACTIVITY)))
				return readByActivitiesCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActivitiesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(ActivityDestination.FIELD_ACTIVITY)};
			return new Object[]{"activitiesCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}