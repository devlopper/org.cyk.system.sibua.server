package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class AdministrativeUnitDestinationPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitDestination> implements AdministrativeUnitDestinationPersistence,ReadAdministrativeUnitDestinationByAdministrativeUnits,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT administrativeUnitDestination FROM AdministrativeUnitDestination administrativeUnitDestination WHERE administrativeUnitDestination.administrativeUnit.code IN :administrativeUnitsCodes");
	}
	
	@Override
	public Collection<AdministrativeUnitDestination> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AdministrativeUnitDestination.FIELD_ADMINISTRATIVE_UNIT)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitDestination.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}