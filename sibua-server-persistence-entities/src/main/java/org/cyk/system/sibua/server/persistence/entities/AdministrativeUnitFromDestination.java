package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity
@Table(name=AdministrativeUnitFromDestination.TABLE_NAME)
public class AdministrativeUnitFromDestination extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_DESTINATION) private Destination destination;
	@ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	@ManyToOne @JoinColumn(name = COLUMN_SERVICE_GROUP) private ServiceGroup serviceGroup;
	@ManyToOne @JoinColumn(name = COLUMN_FUNCTIONAL_CLASSIFICATION) private FunctionalClassification functionalClassification;
	@ManyToOne @JoinColumn(name = COLUMN_LOCALISATION) private Localisation localisation;
	
	@Override
	public AdministrativeUnitFromDestination setCode(String code) {
		return (AdministrativeUnitFromDestination) super.setCode(code);
	}
	
	@Override
	public AdministrativeUnitFromDestination setName(String name) {
		return (AdministrativeUnitFromDestination) super.setName(name);
	}
	
	public AdministrativeUnitFromDestination setSectionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.section = null;
		else
			this.section = InstanceGetter.getInstance().getByBusinessIdentifier(Section.class, code);
		return this;
	}
	
	public AdministrativeUnitFromDestination setServiceGroupFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.serviceGroup = null;
		else
			this.serviceGroup = InstanceGetter.getInstance().getByBusinessIdentifier(ServiceGroup.class, code);
		return this;
	}
	
	public AdministrativeUnitFromDestination setFunctionalClassificationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.functionalClassification = null;
		else
			this.functionalClassification = InstanceGetter.getInstance().getByBusinessIdentifier(FunctionalClassification.class, code);
		return this;
	}
	
	public AdministrativeUnitFromDestination setLocalisationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.localisation = null;
		else
			this.localisation = InstanceGetter.getInstance().getByBusinessIdentifier(Localisation.class, code);
		return this;
	}
	
	public static final String FIELD_DESTINATION = "destination";
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_SERVICE_GROUP = "serviceGroup";
	public static final String FIELD_FUNCTIONAL_CLASSIFICATION = "functionalClassification";
	public static final String FIELD_LOCALISATION = "localisation";
	
	public static final String COLUMN_DESTINATION = Destination.TABLE_NAME;	
	public static final String COLUMN_SECTION = Section.TABLE_NAME;	
	public static final String COLUMN_SERVICE_GROUP = ServiceGroup.TABLE_NAME;	
	public static final String COLUMN_FUNCTIONAL_CLASSIFICATION = FunctionalClassification.TABLE_NAME;	
	public static final String COLUMN_LOCALISATION = Localisation.TABLE_NAME;
	
	public static final String TABLE_NAME = "ua_temp";	
	
}
