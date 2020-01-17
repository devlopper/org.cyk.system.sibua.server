package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class AdministrativeUnitActivityTypeDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public AdministrativeUnitActivityTypeDto setIdentifier(String identifier) {
		return (AdministrativeUnitActivityTypeDto) super.setIdentifier(identifier);
	}

}