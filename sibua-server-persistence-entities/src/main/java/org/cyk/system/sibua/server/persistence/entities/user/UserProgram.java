package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
/*@Entity @Table(name=UserProgram.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserProgram.COLUMN_USER,UserProgram.COLUMN_PROGRAM})
})
*/
public class UserProgram extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_PROGRAM) private Program program;
	
	@Override
	public UserProgram setIdentifier(String identifier) {
		return (UserProgram) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_PROGRAM = "program";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_PROGRAM = Program.TABLE_NAME;
	
	public static final String TABLE_NAME = "programme_demande";	
}