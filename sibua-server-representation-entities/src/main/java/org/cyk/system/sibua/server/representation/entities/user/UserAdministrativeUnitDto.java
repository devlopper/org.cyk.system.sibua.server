package org.cyk.system.sibua.server.representation.entities.user;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class UserAdministrativeUnitDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public UserAdministrativeUnitDto setIdentifier(String identifier) {
		return (UserAdministrativeUnitDto) super.setIdentifier(identifier);
	}

}