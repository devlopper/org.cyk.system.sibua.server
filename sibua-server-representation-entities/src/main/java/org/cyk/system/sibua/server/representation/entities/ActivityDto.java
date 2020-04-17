package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Transient;

import org.cyk.system.sibua.server.representation.entities.user.FunctionTypeDto;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class ActivityDto extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private ActionDto action;
	private FunctionTypeDto functionType;
	private SectionDto section;
	private AdministrativeUnitDto administrativeUnit;
	private AdministrativeUnitDto administrativeUnitBeneficiaire;
	private ArrayList<DestinationDto> destinations;
	private ArrayList<CostUnitDto> costUnits;
	private String natDepCode;
	private String catUsbCode;
	private String catAtvCode;
	private Integer numberOfCostUnits;
	private Boolean isGestionnaire;
	private Boolean isBeneficiaire;
	
	/* As String */	
	@Transient private String asString,sectionAsString,programAsString,actionAsString,functionTypeAsString,managerAsString,beneficiaryAsString;
}