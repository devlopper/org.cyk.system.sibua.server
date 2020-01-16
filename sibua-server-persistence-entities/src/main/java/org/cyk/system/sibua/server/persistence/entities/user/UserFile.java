package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserFile.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserFile.COLUMN_USER,UserFile.COLUMN_FILE,UserFile.FIELD_TYPE})
})
public class UserFile extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_FILE) private File file;
	@NotNull @Enumerated(EnumType.STRING) @Column(name=COLUMN_TYPE) private UserFileType type;
	
	@Column(name = COLUMN_REFERENCE) private String reference;
	
	public UserFile setUserFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.user = null;
		else
			this.user = InstanceGetter.getInstance().getByBusinessIdentifier(User.class, code);
		return this;
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_FILE = "file";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_REFERENCE = "reference";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_FILE = File.TABLE_NAME;
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_REFERENCE = "reference";
	
	public static final String TABLE_NAME = "fichier_joint";	
}
