package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity
@Table(name=AdministrativeUnit.TABLE_NAME,
uniqueConstraints= {
		@UniqueConstraint(name=AdministrativeUnit.UNIQUE_CONSTRAINT_SERVICE_GROUP_FUNCTIONAL_CLASSIFICATION_ORDER_NUMBER
				,columnNames= {AdministrativeUnit.COLUMN_SERVICE_GROUP,AdministrativeUnit.COLUMN_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_ORDER_NUMBER}
		)})
public class AdministrativeUnit extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SERVICE_GROUP) private ServiceGroup serviceGroup;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_FUNCTIONAL_CLASSIFICATION) private FunctionalClassification functionalClassification;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_LOCALISATION) private Localisation localisation;
	@NotNull @Column(name = COLUMN_ORDER_NUMBER) private Integer orderNumber;
	
	@Transient private Collection<Activity> activities;
	
	@Override
	public AdministrativeUnit setCode(String code) {
		return (AdministrativeUnit) super.setCode(code);
	}
	
	@Override
	public AdministrativeUnit setName(String name) {
		return (AdministrativeUnit) super.setName(name);
	}
	
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_SERVICE_GROUP = "serviceGroup";
	public static final String FIELD_FUNCTIONAL_CLASSIFICATION = "functionalClassification";
	public static final String FIELD_LOCALISATION = "localisation";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	public static final String FIELD_ACTIVITIES = "activities";
	
	public static final String COLUMN_SECTION = Section.TABLE_NAME;	
	public static final String COLUMN_SERVICE_GROUP = ServiceGroup.TABLE_NAME;	
	public static final String COLUMN_FUNCTIONAL_CLASSIFICATION = FunctionalClassification.TABLE_NAME;	
	public static final String COLUMN_LOCALISATION = Localisation.TABLE_NAME;
	public static final String COLUMN_ORDER_NUMBER = FIELD_ORDER_NUMBER;	
	
	public static final String TABLE_NAME = "unite_administrative";	
	
	public static final String UNIQUE_CONSTRAINT_SERVICE_GROUP_FUNCTIONAL_CLASSIFICATION_ORDER_NUMBER = COLUMN_SERVICE_GROUP+COLUMN_FUNCTIONAL_CLASSIFICATION+COLUMN_ORDER_NUMBER;
}
