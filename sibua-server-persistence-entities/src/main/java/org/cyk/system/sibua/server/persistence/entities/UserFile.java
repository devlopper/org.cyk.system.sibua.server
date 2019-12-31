package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserFile.TABLE_NAME)
public class UserFile extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @Enumerated(EnumType.STRING) private UserFileType type;
	@NotNull @Lob @Column(name=COLUMN_BYTES) private byte[] bytes;	
	
	public UserFile setUserFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.user = null;
		else
			this.user = InstanceGetter.getInstance().getByBusinessIdentifier(User.class, code);
		return this;
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_BYTES = "bytes";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_BYTES = FIELD_BYTES;
	
	public static final String TABLE_NAME = "fichier";	
}
