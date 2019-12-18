package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class DestinationPersistenceImpl extends AbstractPersistenceEntityImpl<Destination> implements DestinationPersistence,ReadDestinationByAdministrativeUnits,Serializable {
	private static final long serialVersionUID = 1L;

	private String readWhereAdministrativeUnitDoesNotExistBySectionsCodes,readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT destination FROM Destination destination WHERE EXISTS (SELECT administrativeUnitDestination FROM AdministrativeUnitDestination administrativeUnitDestination WHERE administrativeUnitDestination.destination = destination AND administrativeUnitDestination.administrativeUnit.code IN :administrativeUnitsCodes)  ORDER BY destination.code ASC");
		addQueryCollectInstances(readWhereAdministrativeUnitDoesNotExistBySectionsCodes, "SELECT destination FROM Destination destination WHERE destination.section.code IN :sectionsCodes AND NOT EXISTS (SELECT administrativeUnitDestination FROM AdministrativeUnitDestination administrativeUnitDestination WHERE administrativeUnitDestination.destination = destination)  ORDER BY destination.code ASC");
	}
	
	@Override
	public Collection<Destination> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes,Properties properties) {
		if(CollectionHelper.isEmpty(sectionsCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readWhereAdministrativeUnitDoesNotExistBySectionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,sectionsCodes));
	}
	
	@Override
	public Collection<Destination> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
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
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Destination.FIELD_ADMINISTRATIVE_UNIT)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereAdministrativeUnitDoesNotExistBySectionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Destination.FIELD_SECTION)};
			return new Object[]{"sectionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Destination.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}