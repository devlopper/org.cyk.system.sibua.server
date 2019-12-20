package org.cyk.system.sibua.server.persistence.api;

import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface DestinationPersistence extends PersistenceEntity<Destination> {

	Collection<Destination> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes,Properties properties);
	
	default Collection<Destination> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Collection<String> sectionsCodes) {
		if(CollectionHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(sectionsCodes, null);
	}
	
	default Collection<Destination> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(Properties properties,String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), properties);
	}
	
	default Collection<Destination> readWhereAdministrativeUnitDoesNotExistBySectionsCodes(String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereAdministrativeUnitDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), null);
	}
		
	/**/
	
	Collection<Destination> readWhereActivityDoesNotExistBySectionsCodes(Collection<String> sectionsCodes,Properties properties);
	
	default Collection<Destination> readWhereActivityDoesNotExistBySectionsCodes(Collection<String> sectionsCodes) {
		if(CollectionHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereActivityDoesNotExistBySectionsCodes(sectionsCodes, null);
	}
	
	default Collection<Destination> readWhereActivityDoesNotExistBySectionsCodes(Properties properties,String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereActivityDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), properties);
	}
	
	default Collection<Destination> readWhereActivityDoesNotExistBySectionsCodes(String...sectionsCodes){
		if(ArrayHelper.isEmpty(sectionsCodes))
			return null;
		return readWhereActivityDoesNotExistBySectionsCodes(CollectionHelper.listOf(sectionsCodes), null);
	}
	
	/**/
	
	String READ_WHERE_ADMINISTRATIVE_UNIT_DOES_NOT_EXIST_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(Destination.class,"readWhereAdministrativeUnitDoesNotExistBySectionsCodes");
	String READ_WHERE_ACTIVITY_DOES_NOT_EXIST_BY_SECTIONS_CODES = QueryIdentifierBuilder.getInstance().build(Destination.class,"readWhereActivityDoesNotExistBySectionsCodes");
}
