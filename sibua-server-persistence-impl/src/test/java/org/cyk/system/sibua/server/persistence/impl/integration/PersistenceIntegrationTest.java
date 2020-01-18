package org.cyk.system.sibua.server.persistence.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.api.ActionPersistence;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.ProgramPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.api.TitlePersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByPrograms;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class PersistenceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readWhereAdministrativeUnitDoesNotExistBySectionsCodes_() throws Exception{
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
	
	@Test
	public void readWhereAdministrativeUnitByPrograms() throws Exception{
		userTransaction.begin();
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));		
		__inject__(SectionPersistence.class).create(new Section().setCode("1").setName("1"));
		
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("1").setName("1").setOrderNumber(1));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("2").setName("1").setOrderNumber(2));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("3").setName("1").setOrderNumber(3));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("4").setName("1").setOrderNumber(4));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("5").setName("1").setOrderNumber(5));
		
		__inject__(ProgramPersistence.class).create(new Program().setCode("1").setName("1").setSectionFromCode("1"));		
		__inject__(ActionPersistence.class).create(new Action().setCode("1.1").setName("1.1").setProgramFromCode("1"));
		__inject__(ActivityPersistence.class).create(new Activity().setCode("1.1.1").setName("1.1.1").setActionFromCode("1.1"));	
		
		__inject__(ProgramPersistence.class).create(new Program().setCode("2").setName("2").setSectionFromCode("1"));		
		__inject__(ActionPersistence.class).create(new Action().setCode("2.1").setName("2.1").setProgramFromCode("2"));
		__inject__(ActivityPersistence.class).create(new Activity().setCode("2.1.1").setName("2.1.1").setActionFromCode("2.1"));	
		__inject__(ActivityPersistence.class).create(new Activity().setCode("2.1.2").setName("2.1.2").setActionFromCode("2.1"));	
		
		__inject__(ProgramPersistence.class).create(new Program().setCode("3").setName("3").setSectionFromCode("1"));		
		__inject__(ActionPersistence.class).create(new Action().setCode("3.1").setName("3.1").setProgramFromCode("3"));
		__inject__(ActivityPersistence.class).create(new Activity().setCode("3.1.1").setName("3.1.1").setActionFromCode("3.1"));
		userTransaction.commit();
		
		ReadAdministrativeUnitByPrograms readAdministrativeUnitByPrograms = (ReadAdministrativeUnitByPrograms) __inject__(AdministrativeUnitPersistence.class);
		Collection<AdministrativeUnit> administrativeUnits = null;
		
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("1")).isEmpty();
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("2")).isEmpty();
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("3")).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).create(new AdministrativeUnitActivity().setAdministrativeUnitFromCode("1").setActivityFromCode("1.1.1"));
		userTransaction.commit();
		
		administrativeUnits = readAdministrativeUnitByPrograms.readByProgramsCodes("1");
		assertThat(administrativeUnits).isNotNull();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("1");
		
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("2")).isEmpty();
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("3")).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).create(new AdministrativeUnitActivity().setAdministrativeUnitFromCode("2").setActivityFromCode("2.1.1"));
		__inject__(AdministrativeUnitActivityPersistence.class).create(new AdministrativeUnitActivity().setAdministrativeUnitFromCode("3").setActivityFromCode("2.1.2"));
		userTransaction.commit();
		
		administrativeUnits = readAdministrativeUnitByPrograms.readByProgramsCodes("1");
		assertThat(administrativeUnits).isNotNull();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("1");
		
		administrativeUnits = readAdministrativeUnitByPrograms.readByProgramsCodes("2");
		assertThat(administrativeUnits).isNotNull();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("2","3");
		
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("3")).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).deleteAll();
		userTransaction.commit();
		
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("1")).isEmpty();
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("2")).isEmpty();
		assertThat(readAdministrativeUnitByPrograms.readByProgramsCodes("3")).isEmpty();
	}
	
	@Test
	public void administrativeUnit_readChildren() throws Exception{
		userTransaction.begin();
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));		
		__inject__(SectionPersistence.class).create(new Section().setCode("1").setName("1"));
		
		__inject__(AdministrativeUnitPersistence.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1").setOrderNumber(1),new AdministrativeUnit("2","1","1","1","1","1").setOrderNumber(2)
				,new AdministrativeUnit("3","1","1","1","1","1").setOrderNumber(3),new AdministrativeUnit("4","1","1","1","1","1").setOrderNumber(4)
				,new AdministrativeUnit("5","1","1","1","1","1").setOrderNumber(5),new AdministrativeUnit("6","1","1","1","1","1").setOrderNumber(6)
				));
		userTransaction.commit();
		
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().setQueryIdentifier(AdministrativeUnitPersistence.COUNT_CHILDREN_BY_CODES)
				.setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_CODE, List.of("1"))))).isEqualTo(0l);
		
		userTransaction.begin();
		__inject__(AdministrativeUnitHierarchyPersistence.class).createMany(List.of(
				new AdministrativeUnitHierarchy("1","2"),new AdministrativeUnitHierarchy("1","3"),new AdministrativeUnitHierarchy("1","4")
				));
		userTransaction.commit();
		
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().setQueryIdentifier(AdministrativeUnitPersistence.COUNT_CHILDREN_BY_CODES)
				.setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_CODE, List.of("1"))))).isEqualTo(3l);
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().setQueryIdentifier(AdministrativeUnitPersistence.COUNT_CHILDREN_BY_CODES)
				.setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_CODE, List.of("2"))))).isEqualTo(0l);		
	}
	
	@Test
	public void activity_readByFilters() throws Exception{
		userTransaction.begin();
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));	
		__inject__(SectionPersistence.class).createMany(List.of(new Section("1","1"),new Section("2","2")));
		__inject__(ProgramPersistence.class).createMany(List.of(new Program("1","1","1","1"),new Program("2","1","1","1")));
		__inject__(ActionPersistence.class).createMany(List.of(new Action("1","1","1"),new Action("2","1","1")));
		__inject__(ActivityPersistence.class).createMany(List.of(new Activity("atv1","1","1"),new Activity("atv2","1","1"),new Activity("atv3","1","1")
				,new Activity("atv4","1","1"),new Activity("atv5","1","1"),new Activity("atv6","1","1"),new Activity("atv7","1","1")));		
		
		__inject__(AdministrativeUnitPersistence.class).createMany(List.of(
				new AdministrativeUnit("ua1","1","1","1","1","1").setOrderNumber(1),new AdministrativeUnit("ua2","1","1","1","1","1").setOrderNumber(2)
				,new AdministrativeUnit("ua3","1","1","1","1","1").setOrderNumber(3),new AdministrativeUnit("ua4","1","1","1","1","1").setOrderNumber(4)
				,new AdministrativeUnit("ua5","1","1","1","1","1").setOrderNumber(5),new AdministrativeUnit("ua6","1","1","1","1","1").setOrderNumber(6)
				));
		
		__inject__(AdministrativeUnitActivityPersistence.class).createMany(List.of(
				new AdministrativeUnitActivity("ua1","atv1")
			));
		
		userTransaction.commit();
		Collection<Activity> activities = null;
		activities = __inject__(ActivityPersistence.class).read(new Properties().setQueryIdentifier(ActivityPersistence.READ_BY_FILTERS).setQueryFilters(__inject__(Filter.class)));
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).containsExactly("atv1","atv2","atv3","atv4","atv5","atv6","atv7");
		
		activities = __inject__(ActivityPersistence.class).read(new Properties().setQueryIdentifier(ActivityPersistence.READ_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE, List.of("ua1"))));
		assertThat(activities.stream().map(Activity::getCode).collect(Collectors.toList())).containsExactly("atv1");
		
		activities = __inject__(ActivityPersistence.class).read(new Properties().setQueryIdentifier(ActivityPersistence.READ_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(Activity.FIELD_ADMINISTRATIVE_UNIT_GESTIONNAIRE, List.of("ua2"))));
		assertThat(activities).isEmpty();		
	}
	
	//@Test
	public void readWhereDestinationDoesNotExistBySectionsCodes() throws Exception{
		userTransaction.begin();
		__inject__(SectionPersistence.class).createMany(CollectionHelper.listOf(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("1")
				,new Section().setCode("3").setName("1")));
		__inject__(TitlePersistence.class).createMany(CollectionHelper.listOf(new Title().setCode("1").setName("1"),new Title().setCode("2").setName("1")
				,new Title().setCode("3").setName("1")));
		
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("1").setName("1").setOrderNumber(1));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("2").setName("1").setOrderNumber(2));
		__inject__(AdministrativeUnitPersistence.class).create(new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setCode("3").setName("1").setOrderNumber(3));
		
		__inject__(DestinationPersistence.class).createMany(CollectionHelper.listOf(new Destination().setCode("1").setName("1").setSectionFromCode("1").setTitleFromCode("1")
				,new Destination().setCode("2").setName("1").setSectionFromCode("1").setTitleFromCode("1")
				,new Destination().setCode("3").setName("1").setSectionFromCode("1").setTitleFromCode("1")
				,new Destination().setCode("4").setName("1").setSectionFromCode("1").setTitleFromCode("1")
				,new Destination().setCode("5").setName("1").setSectionFromCode("1").setTitleFromCode("1")));
		userTransaction.commit();
		
		assertThat(__inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("1").stream().map(Destination::getCode)
				.collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3","4","5");
		assertThat(__inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("2").stream().map(Destination::getCode)
				.collect(Collectors.toList())).isEmpty();
		
		userTransaction.begin();
		__inject__(AdministrativeUnitDestinationPersistence.class).create(new AdministrativeUnitDestination().setAdministrativeUnitFromCode("1").setDestinationFromCode("1"));
		userTransaction.commit();
		
		Collection<Destination> destinations = __inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("1");
		assertThat(destinations).isNotNull();
		assertThat(destinations.stream().map(Destination::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("2","3","4","5");
		
		userTransaction.begin();
		__inject__(AdministrativeUnitDestinationPersistence.class).create(new AdministrativeUnitDestination().setAdministrativeUnitFromCode("2").setDestinationFromCode("2"));
		__inject__(AdministrativeUnitDestinationPersistence.class).create(new AdministrativeUnitDestination().setAdministrativeUnitFromCode("2").setDestinationFromCode("3"));
		userTransaction.commit();
		
		destinations = __inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("1");
		assertThat(destinations).isNotNull();
		assertThat(destinations.stream().map(Destination::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1");
		
		destinations = __inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("2");
		assertThat(destinations).isNotNull();
		assertThat(destinations.stream().map(Destination::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("2","3");
		
		userTransaction.begin();
		__inject__(AdministrativeUnitActivityPersistence.class).deleteAll();
		userTransaction.commit();
		
		assertThat(__inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("1")).isEmpty();
		assertThat(__inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("2")).isEmpty();
		assertThat(__inject__(DestinationPersistence.class).readWhereAdministrativeUnitDoesNotExistBySectionsCodes("3")).isEmpty();
	}
	
	@Test
	public void readByFilter_administrativeUnit() throws Exception{
		userTransaction.begin();
		__inject__(SectionPersistence.class).create(new Section().setCode("1").setName("1"));
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitPersistence.class).createMany(List.of(
				new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("1")
					.setName("Alice").setOrderNumber(1)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("2")
				.setName("Paul").setOrderNumber(2)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("3")
				.setName("Jean-Yves").setOrderNumber(3)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("4")
				.setName("Komenan").setOrderNumber(4)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("5")
				.setName("Komenan Yao Chrsitian").setOrderNumber(5)
					));
		userTransaction.commit();
		
		__assertReadByFilter__(null,null, 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"", 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null," ", 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"   ", 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"y", 2l, new String[] {"Jean-Yves","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"ya", 1l, new String[] {"Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"kom", 2l, new String[] {"Komenan","Komenan Yao Chrsitian"});
		__assertReadByFilter__(null,"YVES", 1l, new String[] {"Jean-Yves"});
		__assertReadByFilter__(null,"123", 0l, new String[] {});
		
		__assertReadByFilter__("1",null, 1l, new String[] {"Alice"});
		__assertReadByFilter__("2",null, 1l, new String[] {"Paul"});
		__assertReadByFilter__("3",null, 1l, new String[] {"Jean-Yves"});
		__assertReadByFilter__("4",null, 1l, new String[] {"Komenan"});
		__assertReadByFilter__("5",null, 1l, new String[] {"Komenan Yao Chrsitian"});
		
		__assertReadWhereCodeNotInByFilter__(List.of("1"),null, 4l, new String[] {"Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadWhereCodeNotInByFilter__(List.of("1","5"),null, 3l, new String[] {"Paul","Jean-Yves","Komenan"});
		__assertReadWhereCodeNotInByFilter__(List.of("2","4"),null, 3l, new String[] {"Alice","Jean-Yves","Komenan Yao Chrsitian"});
	}
	
	@Test
	public void administrativeUnit_readByFiltersCodesLike() throws Exception{
		userTransaction.begin();
		__inject__(SectionPersistence.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("2"),new Section().setCode("12").setName("12")));
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitPersistence.class).createMany(List.of(
				new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("1")
					.setName("Alice").setOrderNumber(1)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("2")
				.setName("Paul").setOrderNumber(2)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("2").setCode("3")
				.setName("Jean-Yves").setOrderNumber(3)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("2").setCode("4")
				.setName("Komenan").setOrderNumber(4)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("12").setCode("5")
				.setName("Komenan Yao Chrsitian").setOrderNumber(5)
					));
		userTransaction.commit();
		
		__assertReadByFiltersCodesLike__(null,null,null,null,null, 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"",null,null,null, 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null," ",null,null,null, 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"   ",null,null,null, 5l, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"y",null,null,null, 2l, new String[] {"Jean-Yves","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"ya",null,null,null, 1l, new String[] {"Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"kom",null,null,null, 2l, new String[] {"Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,"YVES",null,null,null, 1l, new String[] {"Jean-Yves"});
		__assertReadByFiltersCodesLike__(null,"123",null,null,null, 0l, new String[] {});
		
		__assertReadByFiltersCodesLike__(null,null,"1",null,null, 3l, new String[] {"Alice","Paul","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,null,"2",null,null, 3l, new String[] {"Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersCodesLike__(null,null,"12",null,null, 1l, new String[] {"Komenan Yao Chrsitian"});
		
		/*
		__assertReadByFilter__("1",null, 1l, new String[] {"Alice"});
		__assertReadByFilter__("2",null, 1l, new String[] {"Paul"});
		__assertReadByFilter__("3",null, 1l, new String[] {"Jean-Yves"});
		__assertReadByFilter__("4",null, 1l, new String[] {"Komenan"});
		__assertReadByFilter__("5",null, 1l, new String[] {"Komenan Yao Chrsitian"});
		*/
		
	}
	
	@Test
	public void administrativeUnit_readByFiltersLike() throws Exception{
		userTransaction.begin();
		__inject__(SectionPersistence.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("2"),new Section().setCode("12").setName("12")));
		__inject__(ServiceGroupPersistence.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationPersistence.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationPersistence.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitPersistence.class).createMany(List.of(
				new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("1")
					.setName("Alice").setOrderNumber(1)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setCode("2")
				.setName("Paul").setOrderNumber(2)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("2").setCode("3")
				.setName("Jean-Yves").setOrderNumber(3)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("2").setCode("4")
				.setName("Komenan").setOrderNumber(4)
				,new AdministrativeUnit().setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setLocalisationFromCode("1").setSectionFromCode("2").setCode("12")
				.setName("Komenan Yao Chrsitian").setOrderNumber(5)
					));
		userTransaction.commit();
		
		try {
			__assertReadByFiltersLike__(null,null,null,null,null, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		__assertReadByFiltersLike__("",null,null,null,null, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__(" ",null,null,null,null, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("   ",null,null,null,null, new String[] {"Alice","Paul","Jean-Yves","Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("y",null,null,null,null, new String[] {"Jean-Yves","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("ya",null,null,null,null, new String[] {"Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("kom",null,null,null,null, new String[] {"Komenan","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("YVES",null,null,null,null, new String[] {"Jean-Yves"});
		__assertReadByFiltersLike__("123",null,null,null,null, new String[] {});
		
		__assertReadByFiltersLike__("1",null,null,null,null, new String[] {"Alice","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("2",null,null,null,null, new String[] {"Paul","Komenan Yao Chrsitian"});
		__assertReadByFiltersLike__("12",null,null,null,null, new String[] {"Komenan Yao Chrsitian"});
		
		__assertReadByFiltersLike__("1","1",null,null,null, new String[] {"Alice"});
		__assertReadByFiltersLike__("1","2",null,null,null, new String[] {"Komenan Yao Chrsitian"});
		/*
		__assertReadByFilter__("1",null, 1l, new String[] {"Alice"});
		__assertReadByFilter__("2",null, 1l, new String[] {"Paul"});
		__assertReadByFilter__("3",null, 1l, new String[] {"Jean-Yves"});
		__assertReadByFilter__("4",null, 1l, new String[] {"Komenan"});
		__assertReadByFilter__("5",null, 1l, new String[] {"Komenan Yao Chrsitian"});
		*/
		
	}
	
	@Test
	public void serviceGroup_readWhereBusinessIdentifierOrNameContains() throws Exception{
		userTransaction.begin();
		__inject__(ServiceGroupPersistence.class).createMany(List.of(
			new ServiceGroup().setCode("codeabcd01").setName("nameabcd01")
			,new ServiceGroup().setCode("codeabcd02").setName("nameabcd02")
			,new ServiceGroup().setCode("codeabcd03").setName("nameabcd03")
			,new ServiceGroup().setCode("codeabcd04").setName("nameabcd04")
			,new ServiceGroup().setCode("codeabcd05").setName("nameabcd05")
		));
		userTransaction.commit();
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__(null, new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("", new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("c", new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("code", new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("n", new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("name", new String[] {"codeabcd01","codeabcd02","codeabcd03","codeabcd04","codeabcd05"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("01", new String[] {"codeabcd01"});
		__assertServiceGroupReadWhereBusinessIdentifierOrNameContains__("02", new String[] {"codeabcd02"});
	}
	
	private void __assertServiceGroupReadWhereBusinessIdentifierOrNameContains__(String string,String[] expectedCodes) {
		assertThat(__inject__(ServiceGroupPersistence.class).count(new Properties().
				setQueryIdentifier(ServiceGroupPersistence.COUNT_WHERE_CODE_OR_NAME_CONTAINS).setQueryFilters(__inject__(Filter.class).addField(ServiceGroup.FIELD_CODE, string)
						.addField(ServiceGroup.FIELD_NAME, string)
						))).isEqualTo(Long.valueOf(ArrayHelper.getSize(expectedCodes)));
		
		Collection<ServiceGroup> serviceGroups = __inject__(ServiceGroupPersistence.class).read(new Properties().
				setQueryIdentifier(ServiceGroupPersistence.READ_WHERE_CODE_OR_NAME_CONTAINS).setQueryFilters(__inject__(Filter.class).addField(ServiceGroup.FIELD_CODE, string)
						.addField(ServiceGroup.FIELD_NAME, string)
						));
		
		assertThat(serviceGroups).hasSize(ArrayHelper.getSize(expectedCodes));
		assertThat(serviceGroups.stream().map(ServiceGroup::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder(expectedCodes);
	}
	
	private void __assertReadByFilter__(String code,String name,Long expectedCount,String[] expectedNames) {
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.COUNT_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, List.of("1"))
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_CODE, code)
						))).isEqualTo(expectedCount);
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).read(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.READ_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, List.of("1"))
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_CODE, code)
						));
		
		assertThat(administrativeUnits).hasSize(expectedNames.length);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getName).collect(Collectors.toList())).containsExactlyInAnyOrder(expectedNames);
	}
	
	private void __assertReadByFiltersCodesLike__(String code,String name,String sectionCode,String serviceGroupCode,String functionalClassificationCode,Long expectedCount,String[] expectedNames) {
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.COUNT_BY_FILTERS_CODES_LIKE).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, sectionCode)
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, serviceGroupCode)
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, functionalClassificationCode)
						.addField(AdministrativeUnit.FIELD_CODE, code)
						))).isEqualTo(expectedCount);
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).read(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.READ_BY_FILTERS_CODES_LIKE).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, sectionCode)
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, serviceGroupCode)
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, functionalClassificationCode)
						.addField(AdministrativeUnit.FIELD_CODE, code)
						));
		
		assertThat(administrativeUnits).hasSize(expectedNames.length);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getName).collect(Collectors.toList())).containsExactlyInAnyOrder(expectedNames);
	}
	
	private void __assertReadByFiltersLike__(String administrativeUnit,String section,String serviceGroup,String functionalClassification,String localisation,String[] expected) {
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.COUNT_BY_FILTERS_LIKE).setQueryFilters(__inject__(Filter.class)
						.addField(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, administrativeUnit)						
						.addField(AdministrativeUnit.FIELD_SECTION, section)
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, serviceGroup)
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, functionalClassification)
						.addField(AdministrativeUnit.FIELD_LOCALISATION, localisation)	
						))).isEqualTo(Long.valueOf(ArrayHelper.getSize(expected)));
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).read(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.READ_BY_FILTERS_LIKE).setQueryFilters(__inject__(Filter.class)
						.addField(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, administrativeUnit)
						.addField(AdministrativeUnit.FIELD_SECTION, section)
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, serviceGroup)
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, functionalClassification)
						.addField(AdministrativeUnit.FIELD_LOCALISATION, localisation)						
						));
		
		assertThat(administrativeUnits).hasSize(ArrayHelper.getSize(expected));
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getName).collect(Collectors.toList())).containsExactlyInAnyOrder(expected);
	}
	
	private void __assertReadWhereCodeNotInByFilter__(Collection<String> codes,String name,Long expectedCount,String[] expectedNames) {
		assertThat(__inject__(AdministrativeUnitPersistence.class).count(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.COUNT_WHERE_CODE_NOT_IN_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, List.of("1"))
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_CODE, codes)
						))).isEqualTo(expectedCount);
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).read(new Properties().
				setQueryIdentifier(AdministrativeUnitPersistence.READ_WHERE_CODE_NOT_IN_BY_FILTERS).setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_NAME, name)
						.addField(AdministrativeUnit.FIELD_SECTION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, List.of("1"))
						.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, List.of("1"))
						.addField(AdministrativeUnit.FIELD_CODE, codes)
						));
		
		assertThat(administrativeUnits).hasSize(expectedNames.length);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getName).collect(Collectors.toList())).containsExactlyInAnyOrder(expectedNames);
	}
}
