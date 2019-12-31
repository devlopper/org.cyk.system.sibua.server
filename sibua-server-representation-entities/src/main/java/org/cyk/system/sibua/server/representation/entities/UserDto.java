package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class UserDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private FunctionDto function;
	private String registrationNumber;
	private String firstName;
	private String lastNames;
	private String electronicMailAddress;
	private String phoneNumber;
	private AdministrativeUnitDto administrativeUnit;
	private ArrayList<SectionDto> sectionsManaged;
	private ArrayList<AdministrativeUnitDto> administrativeUnitsManaged;
	private ArrayList<UserFileDto> userFiles;
}