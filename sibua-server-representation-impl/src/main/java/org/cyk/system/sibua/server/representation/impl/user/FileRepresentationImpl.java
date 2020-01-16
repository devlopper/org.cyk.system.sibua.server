package org.cyk.system.sibua.server.representation.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.representation.api.user.FileRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.FileDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class FileRepresentationImpl extends AbstractRepresentationEntityImpl<FileDto> implements FileRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
