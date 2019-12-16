package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class AdministrativeUnitBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnit, AdministrativeUnitPersistence> implements AdministrativeUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteUpdateBefore__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(administrativeUnit, properties, function);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isEmpty(fields))
			return;		
		for(String index : fields.get()) {
			if(AdministrativeUnit.FIELD_ACTIVITIES.equals(index)) {
				Collection<AdministrativeUnitActivity> databaseAdministrativeUnitActivities = ((ReadAdministrativeUnitActivityByAdministrativeUnits)__inject__(AdministrativeUnitActivityPersistence.class)).readByAdministrativeUnits(administrativeUnit);
				Collection<Activity> databaseActivities = CollectionHelper.isEmpty(databaseAdministrativeUnitActivities) ? null : databaseAdministrativeUnitActivities.stream()
						.map(AdministrativeUnitActivity::getActivity).collect(Collectors.toList());
				
				__delete__(administrativeUnit.getActivities(), databaseAdministrativeUnitActivities,AdministrativeUnitActivity.FIELD_ACTIVITY);
				__save__(AdministrativeUnitActivity.class,administrativeUnit.getActivities(), databaseActivities, AdministrativeUnitActivity.FIELD_ACTIVITY, administrativeUnit, AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT);
			}
		}			
	}
	
}
