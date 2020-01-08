package org.cyk.system.sibua.server.business.api;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.business.BusinessEntity;

public interface AdministrativeUnitBusiness extends BusinessEntity<AdministrativeUnit> {

	void generateCodesBySectionsCodes(Collection<String> codes);
	
	default void generateCodesBySectionsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return;
		generateCodesBySectionsCodes(CollectionHelper.listOf(codes));
	}
	
	default void generateCodesBySections(Collection<Section> sections) {
		if(CollectionHelper.isEmpty(sections))
			return;
		generateCodesBySectionsCodes(sections.stream().map(Section::getCode).collect(Collectors.toList()));
	}
	
	default void generateCodesBySections(Section...sections) {
		if(ArrayHelper.isEmpty(sections))
			return;
		generateCodesBySections(CollectionHelper.listOf(sections));
	}
}