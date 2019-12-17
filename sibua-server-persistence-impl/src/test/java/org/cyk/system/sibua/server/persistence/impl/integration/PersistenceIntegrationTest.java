package org.cyk.system.sibua.server.persistence.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.api.ActionPersistence;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.ProgramPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class PersistenceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readWhereAdministrativeUnitDoesNotExistBySectionsCodes() throws Exception{
		userTransaction.begin();
		Section section = new Section().setCode("01").setName("01");
		__inject__(SectionPersistence.class).create(section);
		
		Program program = new Program().setCode("01").setName("01").setSection(section);
		__inject__(ProgramPersistence.class).create(program);
		
		Action action = new Action().setCode("01").setName("01").setProgram(program);
		__inject__(ActionPersistence.class).create(action);
		
		ServiceGroup serviceGroup = new ServiceGroup().setCode("01").setName("01");
		__inject__(ServiceGroupPersistence.class).create(serviceGroup);
		FunctionalClassification functionalClassification = new FunctionalClassification().setCode("01").setName("01");
		__inject__(FunctionalClassificationPersistence.class).create(functionalClassification);
		Localisation localisation = new Localisation().setCode("01").setName("01");
		__inject__(LocalisationPersistence.class).create(localisation);
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setServiceGroup(serviceGroup).setFunctionalClassification(functionalClassification)
				.setLocalisation(localisation).setSection(section).setCode("01").setName("01").setOrderNumber(1);
		__inject__(AdministrativeUnitPersistence.class).create(administrativeUnit);
		userTransaction.commit();
		
		Collection<Activity> activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("01");
		assertThat(activities).isEmpty();
		
		userTransaction.begin();
		Activity activity = new Activity().setCode("01").setName("01").setAction(action);
		__inject__(ActivityPersistence.class).create(activity);
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("01");
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).contains("01");
		
		userTransaction.begin();
		AdministrativeUnitActivity administrativeUnitActivity = new AdministrativeUnitActivity().setAdministrativeUnit(administrativeUnit).setActivity(activity);
		__inject__(AdministrativeUnitActivityPersistence.class).create(administrativeUnitActivity);
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("01");
		assertThat(activities).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).deleteAll();
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("01");
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).contains("01");
	}
	
	@Test
	public void readWhereAdministrativeUnitDoesNotExistByProgramsCodes() throws Exception{
		userTransaction.begin();
		Section section = new Section().setCode("01").setName("01");
		__inject__(SectionPersistence.class).create(section);
		
		Program program = new Program().setCode("01").setName("01").setSection(section);
		__inject__(ProgramPersistence.class).create(program);
		
		Action action = new Action().setCode("01").setName("01").setProgram(program);
		__inject__(ActionPersistence.class).create(action);
		
		ServiceGroup serviceGroup = new ServiceGroup().setCode("01").setName("01");
		__inject__(ServiceGroupPersistence.class).create(serviceGroup);
		FunctionalClassification functionalClassification = new FunctionalClassification().setCode("01").setName("01");
		__inject__(FunctionalClassificationPersistence.class).create(functionalClassification);
		Localisation localisation = new Localisation().setCode("01").setName("01");
		__inject__(LocalisationPersistence.class).create(localisation);
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setServiceGroup(serviceGroup).setFunctionalClassification(functionalClassification)
				.setLocalisation(localisation).setSection(section).setCode("01").setName("01").setOrderNumber(1);
		__inject__(AdministrativeUnitPersistence.class).create(administrativeUnit);
		userTransaction.commit();
		
		Collection<Activity> activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistByProgramsCodes("01");
		assertThat(activities).isEmpty();
		
		userTransaction.begin();
		Activity activity = new Activity().setCode("01").setName("01").setAction(action);
		__inject__(ActivityPersistence.class).create(activity);
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistByProgramsCodes("01");
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).contains("01");
		
		userTransaction.begin();
		AdministrativeUnitActivity administrativeUnitActivity = new AdministrativeUnitActivity().setAdministrativeUnit(administrativeUnit).setActivity(activity);
		__inject__(AdministrativeUnitActivityPersistence.class).create(administrativeUnitActivity);
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistByProgramsCodes("01");
		assertThat(activities).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).deleteAll();
		userTransaction.commit();
		
		activities = __inject__(ActivityPersistence.class).readWhereAdministrativeUnitDoesNotExistByProgramsCodes("01");
		assertThat(activities).isNotNull();
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).contains("01");
	}
	
}
