package org.cyk.system.sibua.server.persistence.api;

import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface AdministrativeUnitActivityPersistence extends PersistenceEntity<AdministrativeUnitActivity> {

	Collection<AdministrativeUnitActivity> readByAdministrativeUnitsCodesByActivitiesCodes(Collection<String> administrativeUnitsCodes,Collection<String> activitiesCodes,Properties properties);
	
	Collection<AdministrativeUnitActivity> readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes(Collection<String> administrativeUnitsCodes,Properties properties);
	
	Collection<AdministrativeUnitActivity> readByAdministrativeUnitBeneficiairesCodes(Collection<String> codes, Properties properties);
}
