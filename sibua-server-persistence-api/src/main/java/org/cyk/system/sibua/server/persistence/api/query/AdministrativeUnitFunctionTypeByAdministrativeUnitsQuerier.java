package org.cyk.system.sibua.server.persistence.api.query;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFunctionType;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.persistence.query.ByDimensionOneQuerier;
import org.cyk.utility.__kernel__.persistence.query.annotation.Queries;
import org.cyk.utility.__kernel__.persistence.query.annotation.Query;
import org.cyk.utility.__kernel__.value.Value;

@Queries(value = {
		@Query(tupleClass = AdministrativeUnitFunctionType.class,name = AdministrativeUnitFunctionTypeByAdministrativeUnitsQuerier.QUERY_NAME_READ,value = "SELECT tuple FROM AdministrativeUnitFunctionType tuple WHERE tuple.administrativeUnit.code IN :"+AdministrativeUnitFunctionTypeByAdministrativeUnitsQuerier.PARAMETER_NAME_ADMINISTRATIVE_UNITS_CODES)
})
public interface AdministrativeUnitFunctionTypeByAdministrativeUnitsQuerier extends ByDimensionOneQuerier<AdministrativeUnitFunctionType, String,AdministrativeUnit,String> {

	/**/
	
	static AdministrativeUnitFunctionTypeByAdministrativeUnitsQuerier getInstance() {
		return Helper.getInstance(AdministrativeUnitFunctionTypeByAdministrativeUnitsQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String QUERY_NAME_READ = "readByAdministrativeUnitsCodes";
	String QUERY_NAME_COUNT = "countByAdministrativeUnitsCodes";
	String PARAMETER_NAME_ADMINISTRATIVE_UNITS_CODES = "administrativeUnitsCodes";
}
