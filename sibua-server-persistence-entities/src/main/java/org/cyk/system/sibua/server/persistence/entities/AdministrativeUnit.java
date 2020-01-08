package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity
@Table(name=AdministrativeUnit.TABLE_NAME/*,
uniqueConstraints= {
		@UniqueConstraint(name=AdministrativeUnit.UNIQUE_CONSTRAINT_SERVICE_GROUP_FUNCTIONAL_CLASSIFICATION_ORDER_NUMBER
				,columnNames= {AdministrativeUnit.COLUMN_SERVICE_GROUP,AdministrativeUnit.COLUMN_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_ORDER_NUMBER}
		)}*/)
public class AdministrativeUnit extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SERVICE_GROUP) private ServiceGroup serviceGroup;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_FUNCTIONAL_CLASSIFICATION) private FunctionalClassification functionalClassification;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_LOCALISATION) private Localisation localisation;
	@NotNull @Column(name = COLUMN_ORDER_NUMBER) private Integer orderNumber;
	
	@Transient private Collection<Program> programs;
	@Transient private Collection<Activity> activities;
	@Transient private AdministrativeUnit parent;
	@Transient private Collection<AdministrativeUnit> parents;
	@Transient private Collection<AdministrativeUnit> children;
	@Transient private Collection<Destination> destinations;
	@Transient private Collection<ActivityDestination> activityDestinations;
	
	public AdministrativeUnit(String code,String name,String sectionCode,String serviceGroupCode,String functionalClassificationCode,String localisationCode) {
		super(code, name);
		setSectionFromCode(sectionCode);
		setServiceGroupFromCode(serviceGroupCode);
		setFunctionalClassificationFromCode(functionalClassificationCode);
		setLocalisationFromCode(localisationCode);
	}
	
	@Override
	public AdministrativeUnit setCode(String code) {
		return (AdministrativeUnit) super.setCode(code);
	}
	
	@Override
	public AdministrativeUnit setName(String name) {
		return (AdministrativeUnit) super.setName(name);
	}
	
	public AdministrativeUnit setSectionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.section = null;
		else
			this.section = InstanceGetter.getInstance().getByBusinessIdentifier(Section.class, code);
		return this;
	}
	
	public AdministrativeUnit setServiceGroupFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.serviceGroup = null;
		else
			this.serviceGroup = InstanceGetter.getInstance().getByBusinessIdentifier(ServiceGroup.class, code);
		return this;
	}
	
	public AdministrativeUnit setFunctionalClassificationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.functionalClassification = null;
		else
			this.functionalClassification = InstanceGetter.getInstance().getByBusinessIdentifier(FunctionalClassification.class, code);
		return this;
	}
	
	public AdministrativeUnit setLocalisationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.localisation = null;
		else
			this.localisation = InstanceGetter.getInstance().getByBusinessIdentifier(Localisation.class, code);
		return this;
	}
	
	public AdministrativeUnit setParentFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.parent = null;
		else
			this.parent = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public Collection<Activity> getActivities(Boolean injectIfNull) {
		if(activities == null && Boolean.TRUE.equals(injectIfNull))
			activities = new ArrayList<>();
		return this.activities;
	}
	
	public AdministrativeUnit addActivitiesByCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return this;
		for(String code : codes) {
			Activity activity = InstanceGetter.getInstance().getByBusinessIdentifier(Activity.class, code);
			if(activity == null)
				continue;
			getActivities(Boolean.TRUE).add(activity);
		}
		return this;
	}
	
	public AdministrativeUnit addActivitiesByCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return this;
		return addActivitiesByCodes(CollectionHelper.listOf(codes));
	}
	
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_SERVICE_GROUP = "serviceGroup";
	public static final String FIELD_FUNCTIONAL_CLASSIFICATION = "functionalClassification";
	public static final String FIELD_LOCALISATION = "localisation";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	public static final String FIELD_ACTIVITIES = "activities";
	public static final String FIELD_PROGRAMS = "programs";
	public static final String FIELD_DESTINATIONS = "destinations";
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_CHILDREN = "children";
	public static final String FIELD_ACTIVITY_DESTINATIONS = "activityDestinations";
	
	public static final String COLUMN_SECTION = Section.TABLE_NAME;	
	public static final String COLUMN_SERVICE_GROUP = ServiceGroup.TABLE_NAME;	
	public static final String COLUMN_FUNCTIONAL_CLASSIFICATION = FunctionalClassification.TABLE_NAME;	
	public static final String COLUMN_LOCALISATION = Localisation.TABLE_NAME;
	public static final String COLUMN_ORDER_NUMBER = FIELD_ORDER_NUMBER;	
	
	public static final String TABLE_NAME = "unite_administrative";	
	
	public static final String UNIQUE_CONSTRAINT_SERVICE_GROUP_FUNCTIONAL_CLASSIFICATION_ORDER_NUMBER = COLUMN_SERVICE_GROUP+COLUMN_FUNCTIONAL_CLASSIFICATION+COLUMN_ORDER_NUMBER;
}
