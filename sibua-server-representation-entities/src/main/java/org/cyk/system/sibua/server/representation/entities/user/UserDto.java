package org.cyk.system.sibua.server.representation.entities.user;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.system.sibua.server.representation.entities.ActivityDto;
import org.cyk.system.sibua.server.representation.entities.AdministrativeUnitDto;
import org.cyk.system.sibua.server.representation.entities.LocalisationDto;
import org.cyk.system.sibua.server.representation.entities.SectionDto;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class UserDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserTypeDto type;
	private CivilityDto civility;
	private AdministrativeUnitDto administrativeUnit;
	
	private String registrationNumber;
	private String firstName;
	private String lastNames;
	private String electronicMailAddress;
	private String mobilePhoneNumber;
	private String deskPhoneNumber;
	private String deskPost;
	private String postalAddress;
	private String certificateReference;
	
	private String creationDate;
	private String activationDate;
	private String validationDate;
	
	private String accessToken;
	
	private String reportUniformResourceIdentifier;
	
	private ArrayList<SectionDto> sections;
	private ArrayList<AdministrativeUnitDto> administrativeUnits;
	private ArrayList<FileDto> files;
	private ArrayList<FunctionDto> functions;
	private ArrayList<ActivityDto> activities;
	private ArrayList<LocalisationDto> localisations;
	
	/**/
	
	public static final String REPORT_UNIFORM_RESOURCE_IDENTIFIER_PATH_FORMAT = "/%s/download";
}