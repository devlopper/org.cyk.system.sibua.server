package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserSection.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserSection.COLUMN_USER,UserSection.COLUMN_SECTION})
})
public class UserSection extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	
	@Override
	public UserSection setIdentifier(String identifier) {
		return (UserSection) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_SECTION = "section";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_SECTION = Section.TABLE_NAME;
	
	public static final String TABLE_NAME = "section_demandee";	
}