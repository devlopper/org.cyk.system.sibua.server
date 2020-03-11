package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.ActivityBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitActivityBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByActivities;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class ActivityBusinessImpl extends AbstractBusinessEntityImpl<Activity, ActivityPersistence> implements ActivityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenExecuteUpdateBefore__(Activity activity, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(activity, properties, function);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isEmpty(fields))
			return;	
		for(String index : fields.get()) {
			if(Activity.FIELD_DESTINATIONS.equals(index)) {
				Collection<ActivityDestination> databaseActivityDestinations = ((ReadActivityDestinationByActivities)__inject__(ActivityDestinationPersistence.class)).readByActivities(activity);
				Collection<Destination> databaseDestinations = CollectionHelper.isEmpty(databaseActivityDestinations) ? null : databaseActivityDestinations.stream()
						.map(ActivityDestination::getDestination).collect(Collectors.toList());
				
				__delete__(activity.getDestinations(), databaseActivityDestinations,ActivityDestination.FIELD_DESTINATION);
				__save__(ActivityDestination.class,activity.getDestinations(), databaseDestinations, ActivityDestination.FIELD_DESTINATION, activity, ActivityDestination.FIELD_ACTIVITY);
			}else if(Activity.FIELD_ADMINISTRATIVE_UNIT.equals(index)) {
				Activity databaseActivity = __inject__(ActivityPersistence.class).readBySystemIdentifier(activity.getIdentifier(),new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
				if(databaseActivity != null) {
					AdministrativeUnitActivity administrativeUnitActivity = null;
					if(databaseActivity.getAdministrativeUnit() != null)
						administrativeUnitActivity = CollectionHelper.getFirst(__inject__(AdministrativeUnitActivityPersistence.class).readByAdministrativeUnitsCodesByActivitiesCodes(
							List.of(databaseActivity.getAdministrativeUnit().getCode()), List.of(activity.getCode()), null));
					if(administrativeUnitActivity == null)
						administrativeUnitActivity = CollectionHelper.getFirst( 
								((ReadAdministrativeUnitActivityByActivities) __inject__(AdministrativeUnitActivityPersistence.class)).readByActivities(activity));
					if(activity.getAdministrativeUnit() != null || activity.getAdministrativeUnitBeneficiaire() != null) {
						//save link
						if(administrativeUnitActivity == null) {
							administrativeUnitActivity = new AdministrativeUnitActivity();
						}
						administrativeUnitActivity.setActivity(activity).setAdministrativeUnit(activity.getAdministrativeUnit()).setAdministrativeUnitBeneficiaire(activity.getAdministrativeUnitBeneficiaire());
						__inject__(AdministrativeUnitActivityBusiness.class).save(administrativeUnitActivity);
					}else {
						//delete link
						if(administrativeUnitActivity != null)
							__inject__(AdministrativeUnitActivityBusiness.class).delete(administrativeUnitActivity);
					}	
				}
			}
		}
		activity.setModificationDate(LocalDateTime.now());
	}
}
