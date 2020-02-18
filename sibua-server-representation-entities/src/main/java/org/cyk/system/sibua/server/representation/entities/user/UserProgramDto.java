package org.cyk.system.sibua.server.representation.entities.user;

import java.io.Serializable;

import org.cyk.system.sibua.server.representation.entities.ProgramDto;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class UserProgramDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UserDto user;
	private ProgramDto program;
	
	@Override
	public UserProgramDto setIdentifier(String identifier) {
		return (UserProgramDto) super.setIdentifier(identifier);
	}

}