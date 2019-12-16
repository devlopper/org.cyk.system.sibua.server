package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByAdministrativeUnits<ENTITY> {

	Collection<ENTITY> readByAdministrativeUnitsCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readByAdministrativeUnitsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(codes,null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnitsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnitsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Collection<AdministrativeUnit> administrativeUnits,Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnitsCodes(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Collection<AdministrativeUnit> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(administrativeUnits,null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Properties properties,AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),null);
	}
}
