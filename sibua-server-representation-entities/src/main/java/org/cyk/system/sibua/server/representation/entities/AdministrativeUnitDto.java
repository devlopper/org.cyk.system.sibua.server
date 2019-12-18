package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class AdministrativeUnitDto extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SectionDto section;
	private ServiceGroupDto serviceGroup;
	private FunctionalClassificationDto functionalClassification;
	private LocalisationDto localisation;
	private Integer orderNumber;
	private AdministrativeUnitDto parent;
	
	private ArrayList<AdministrativeUnitDto> parents;
	private ArrayList<AdministrativeUnitDto> children;
	private ArrayList<ActivityDto> activities;
	private ArrayList<DestinationDto> destinations;
}