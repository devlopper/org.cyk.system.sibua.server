package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;

import org.cyk.system.sibua.server.representation.entities.user.FunctionTypeDto;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class AdministrativeUnitFunctionTypeDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private AdministrativeUnitDto administrativeUnit;
	private FunctionTypeDto functionType;
	
	@Override
	public AdministrativeUnitFunctionTypeDto setIdentifier(String identifier) {
		return (AdministrativeUnitFunctionTypeDto) super.setIdentifier(identifier);
	}

}