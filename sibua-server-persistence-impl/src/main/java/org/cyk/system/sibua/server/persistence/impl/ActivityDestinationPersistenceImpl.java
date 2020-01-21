package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class ActivityDestinationPersistenceImpl extends AbstractPersistenceEntityImpl<ActivityDestination> implements ActivityDestinationPersistence,ReadActivityDestinationByAdministrativeUnits,ReadActivityDestinationByActivities,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByActivitiesCodes,readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByActivitiesCodes, "SELECT activityDestination FROM ActivityDestination activityDestination WHERE activityDestination.activity.code IN :activitiesCodes");
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT activityDestination FROM ActivityDestination activityDestination WHERE "
				+ "EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = activityDestination.activity "
				+ "AND administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes)");
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
	public Collection<ActivityDestination> readByAdministrativeUnitsCodes(Collection<String> codes,Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Long countByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		return null;
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
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActivitiesCodes)) {
			return new Object[]{"activitiesCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}