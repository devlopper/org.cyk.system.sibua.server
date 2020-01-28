package org.cyk.system.sibua.server.representation.entities.user;

import java.io.Serializable;

import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class FileDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uniformResourceLocator;
	
	private byte[] bytes;
	
	private String name;
	private String extension;
	private String mimeType;
	private Long size;
	private String nameAndExtension;
	private String sha1;
	private String text;
	private String reference;
	
	private UserFileType type;
	
	@Override
	public FileDto setIdentifier(String identifier) {
		return (FileDto) super.setIdentifier(identifier);
	}

}