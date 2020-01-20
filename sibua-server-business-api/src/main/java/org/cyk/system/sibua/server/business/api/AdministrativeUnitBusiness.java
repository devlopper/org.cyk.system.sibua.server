package org.cyk.system.sibua.server.business.api;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.business.BusinessEntity;

public interface AdministrativeUnitBusiness extends BusinessEntity<AdministrativeUnit> {

	void generateCodesBySectionsCodes(Collection<String> codes);
	
	default void generateCodesBySectionsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return;
		generateCodesBySectionsCodes(CollectionHelper.listOf(codes));
	}
	
	default void generateCodesBySections(Collection<Section> sections) {
		if(CollectionHelper.isEmpty(sections))
			return;
		generateCodesBySectionsCodes(sections.stream().map(Section::getCode).collect(Collectors.toList()));
	}
	
	default void generateCodesBySections(Section...sections) {
		if(ArrayHelper.isEmpty(sections))
			return;
		generateCodesBySections(CollectionHelper.listOf(sections));
	}
	
	/**/
	/*
	void moveActivitiesByCodes(Collection<String> activitiesCodes,String administrativeUnitCode);
	
	default void moveActivitiesByCodes(String administrativeUnitCode,String...activitiesCodes) {
		if(StringHelper.isBlank(administrativeUnitCode) || ArrayHelper.isEmpty(activitiesCodes))
			return;
		moveActivitiesByCodes(CollectionHelper.listOf(activitiesCodes),administrativeUnitCode);
	}
	
	default void moveActivities(String administrativeUnitCode,Collection<Activity> activities) {
		if(StringHelper.isBlank(administrativeUnitCode) || CollectionHelper.isEmpty(activities))
			return;
		moveActivitiesByCodes(activities.stream().map(Activity::getCode).collect(Collectors.toList()),administrativeUnitCode);
	}
	
	default void moveActivities(String administrativeUnitCode,Activity...activities) {
		if(StringHelper.isBlank(administrativeUnitCode) || ArrayHelper.isEmpty(activities))
			return;
		moveActivities(administrativeUnitCode,CollectionHelper.listOf(activities));
	}
	*/
	/**/
	
	void mergeByCodes(Collection<String> administrativeUnitsSourcesCodes,String administrativeUnitDestinationCode);
	/*
	default void mergeByCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return;
		mergeByCodes(CollectionHelper.listOf(codes));
	}
	
	default void merge(Collection<AdministrativeUnit> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return;
		mergeByCodes(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList()));
	}
	
	default void merge(AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return;
		merge(CollectionHelper.listOf(administrativeUnits));
	}
	*/
	
}