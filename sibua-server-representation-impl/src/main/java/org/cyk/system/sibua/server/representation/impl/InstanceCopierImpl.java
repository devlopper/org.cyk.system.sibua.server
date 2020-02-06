package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.instance.AbstractInstanceCopierImpl;

@org.cyk.system.sibua.server.annotation.System @Deprecated
public class InstanceCopierImpl extends AbstractInstanceCopierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	@Override
	public void copy(Object source, Object destination, Map<String, String> fieldsNamesMap) {
		if(source instanceof UserDto && destination instanceof User) {
			UserDto userDto = (UserDto) source;
			User user = (User) destination;
			user.setAdministrativeUnitCertificateSignedDate(LocalDateTime.from(temporal));
		}
		try {
			super.copy(source, destination, fieldsNamesMap);
		} catch (Exception exception) {
			
			
			
			System.out.println("InstanceCopierImpl.copy() :::: "+userDto.getAdministrativeUnitCertificateSignedDate());
			//user.setAdministrativeUnitCertificateSignedDate(LocalDateTime.parse(userDto.getAdministrativeUnitCertificateSignedDate()));
			e.printStackTrace();
		}
		
	}
	*/
}