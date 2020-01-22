package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

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
//@javax.persistence.Entity
@Table(name=AdministrativeUnitHierarchy.TABLE_NAME,
uniqueConstraints= {
		@UniqueConstraint(name=AdministrativeUnitHierarchy.UNIQUE_CONSTRAINT_PARENT_CHILD
				,columnNames= {AdministrativeUnitHierarchy.COLUMN_PARENT,AdministrativeUnitHierarchy.COLUMN_CHILD}
		)})
public class AdministrativeUnitHierarchy extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_PARENT) private AdministrativeUnit parent;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_CHILD) private AdministrativeUnit child;
	
	public AdministrativeUnitHierarchy(String parentCode,String childCode) {
		setParentFromCode(parentCode);
		setChildFromCode(childCode);
	}
	
	public AdministrativeUnitHierarchy setParentFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.parent = null;
		else
			this.parent = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public AdministrativeUnitHierarchy setChildFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.child = null;
		else
			this.child = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_CHILD = "child";
	
	public static final String COLUMN_PARENT = FIELD_PARENT;	
	public static final String COLUMN_CHILD = FIELD_CHILD;	
	
	public static final String TABLE_NAME = "ua_hierarchie";	
	
	public static final String UNIQUE_CONSTRAINT_PARENT_CHILD = COLUMN_PARENT+COLUMN_CHILD;
}
