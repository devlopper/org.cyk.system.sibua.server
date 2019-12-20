package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByActivities<ENTITY> {

	Collection<ENTITY> readByActivitiesCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readByActivitiesCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByActivitiesCodes(codes,null);
	}
	
	default Collection<ENTITY> readByActivitiesCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByActivitiesCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByActivitiesCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByActivitiesCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByActivities(Collection<Activity> activities,Properties properties) {
		if(CollectionHelper.isEmpty(activities))
			return null;
		return readByActivitiesCodes(activities.stream().map(Activity::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByActivities(Collection<Activity> activities) {
		if(CollectionHelper.isEmpty(activities))
			return null;
		return readByActivities(activities,null);
	}
	
	default Collection<ENTITY> readByActivities(Properties properties,Activity...activities) {
		if(ArrayHelper.isEmpty(activities))
			return null;
		return readByActivities(CollectionHelper.listOf(activities),properties);
	}
	
	default Collection<ENTITY> readByActivities(Activity...activities) {
		if(ArrayHelper.isEmpty(activities))
			return null;
		return readByActivities(CollectionHelper.listOf(activities),null);
	}
}
