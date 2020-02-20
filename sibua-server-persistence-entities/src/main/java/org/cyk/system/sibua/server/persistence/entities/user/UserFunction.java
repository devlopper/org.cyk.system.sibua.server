package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserFunction.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserFunction.COLUMN_USER,UserFunction.COLUMN_FUNCTION})
})
public class UserFunction extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_FUNCTION) private Function function;
	
	public UserFunction(User user,Function function) {
		this.user = user;
		this.function = function;
	}
	
	@Override
	public UserFunction setIdentifier(String identifier) {
		return (UserFunction) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_FUNCTION = "function";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_FUNCTION = Function.TABLE_NAME;
	
	public static final String TABLE_NAME = "fonction_demandee";	
}