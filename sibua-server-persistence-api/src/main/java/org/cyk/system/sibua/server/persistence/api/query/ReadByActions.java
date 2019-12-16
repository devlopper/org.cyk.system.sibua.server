package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByActions<ENTITY> {

	Collection<ENTITY> readByActionsCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readByActionsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByActionsCodes(codes,null);
	}
	
	default Collection<ENTITY> readByActionsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByActionsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByActionsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByActionsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByActions(Collection<Action> actions,Properties properties) {
		if(CollectionHelper.isEmpty(actions))
			return null;
		return readByActionsCodes(actions.stream().map(Action::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByActions(Collection<Action> actions) {
		if(CollectionHelper.isEmpty(actions))
			return null;
		return readByActions(actions,null);
	}
	
	default Collection<ENTITY> readByActions(Properties properties,Action...actions) {
		if(ArrayHelper.isEmpty(actions))
			return null;
		return readByActions(CollectionHelper.listOf(actions),properties);
	}
	
	default Collection<ENTITY> readByActions(Action...actions) {
		if(ArrayHelper.isEmpty(actions))
			return null;
		return readByActions(CollectionHelper.listOf(actions),null);
	}
}
