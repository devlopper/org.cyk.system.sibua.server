package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Title.TABLE_NAME)
public class Title extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Title setCode(String code) {
		return (Title) super.setCode(code);
	}
	
	@Override
	public Title setName(String name) {
		return (Title) super.setName(name);
	}
	
	public static final String TABLE_NAME = "titre";	
}
