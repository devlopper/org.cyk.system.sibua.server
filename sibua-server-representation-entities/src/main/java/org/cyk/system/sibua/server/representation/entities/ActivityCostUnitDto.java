package org.cyk.system.sibua.server.representation.entities;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class ActivityCostUnitDto extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private ActivityDto activity;
	private CostUnitDto costUnit;
	private Long financementAE;
	private Long financementCP;
	private Long arbitrageAE;
	private Long arbitrageCP;
	private Long budgetAE;
	private Long budgetCP;
	private String procedure;
	private String exempted;
	
	@Override
	public ActivityCostUnitDto setIdentifier(String identifier) {
		return (ActivityCostUnitDto) super.setIdentifier(identifier);
	}

}