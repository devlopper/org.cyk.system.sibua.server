package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByDestinations<ENTITY> {

	Collection<ENTITY> readByDestinationsCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readByDestinationsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByDestinationsCodes(codes,null);
	}
	
	default Collection<ENTITY> readByDestinationsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByDestinationsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByDestinationsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByDestinationsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByDestinations(Collection<Destination> destinations,Properties properties) {
		if(CollectionHelper.isEmpty(destinations))
			return null;
		return readByDestinationsCodes(destinations.stream().map(Destination::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByDestinations(Collection<Destination> destinations) {
		if(CollectionHelper.isEmpty(destinations))
			return null;
		return readByDestinations(destinations,null);
	}
	
	default Collection<ENTITY> readByDestinations(Properties properties,Destination...destinations) {
		if(ArrayHelper.isEmpty(destinations))
			return null;
		return readByDestinations(CollectionHelper.listOf(destinations),properties);
	}
	
	default Collection<ENTITY> readByDestinations(Destination...destinations) {
		if(ArrayHelper.isEmpty(destinations))
			return null;
		return readByDestinations(CollectionHelper.listOf(destinations),null);
	}
}
