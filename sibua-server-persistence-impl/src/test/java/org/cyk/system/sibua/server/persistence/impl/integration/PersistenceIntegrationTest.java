package org.cyk.system.sibua.server.persistence.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.api.ActionPersistence;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
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
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.system.sibua.server.persistence.entities.Title;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.filter.Filter;
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
