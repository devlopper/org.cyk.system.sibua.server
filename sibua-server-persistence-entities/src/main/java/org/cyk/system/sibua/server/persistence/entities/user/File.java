package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=File.TABLE_NAME)
public class File extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Lob @Column(name=COLUMN_BYTES) private byte[] bytes;	
	@NotNull @Column(name=COLUMN_EXTENSION) private String extension;
	@NotNull @Column(name=COLUMN_SHA_1,unique = true) private String sha1;
	
	@Transient private String name;
	@Transient private UserFileType type;
	@Transient private String reference;
	
	@Override
	public File setIdentifier(String identifier) {
		return (File) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_BYTES = "bytes";
	public static final String FIELD_EXTENSION = "extension";
	public static final String FIELD_SHA_1 = "sha1";
	
	public static final String COLUMN_BYTES = "binaire";
	public static final String COLUMN_EXTENSION = "extension";
	public static final String COLUMN_SHA_1 = "sha1";
	
	public static final String TABLE_NAME = "fichier";	
}