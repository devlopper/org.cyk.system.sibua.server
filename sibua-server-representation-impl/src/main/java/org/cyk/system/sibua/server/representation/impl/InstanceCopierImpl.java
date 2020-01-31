package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.representation.entities.user.FileDto;
import org.cyk.system.sibua.server.representation.entities.user.UserDto;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.instance.AbstractInstanceCopierImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

@org.cyk.system.sibua.server.annotation.System
public class InstanceCopierImpl extends AbstractInstanceCopierImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void copy(Object source, Object destination, Map<String, String> fieldsNamesMap) {
		if(source instanceof UserDto && destination instanceof User) {
			if(CollectionHelper.isNotEmpty(((UserDto)source).getFiles())) {
				((User)destination).setFiles(new ArrayList<File>());
				for(FileDto fileDto : ((UserDto)source).getFiles()) {
					File file = StringHelper.isBlank(fileDto.getIdentifier()) 
							? new File().setBytes(fileDto.getBytes()).setExtension(fileDto.getExtension()).setReference(fileDto.getReference()).setType(fileDto.getType()) 
							: DependencyInjection.inject(FilePersistence.class).readBySystemIdentifier(fileDto.getIdentifier()).setReference(fileDto.getReference());
					((User)destination).getFiles().add(file);
				}
			}
		}else
			super.copy(source, destination, fieldsNamesMap);
	}
	
	@Override
	public void copy(Object source, Object destination, Collection<String> fieldsNames) {
		System.out.println("InstanceCopierImpl.copy() "+source+" : "+destination+" : "+fieldsNames);
		super.copy(source, destination, fieldsNames);
		
	}
	
}