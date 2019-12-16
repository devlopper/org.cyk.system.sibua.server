package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadBySections<ENTITY> {

	Collection<ENTITY> readBySectionsCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readBySectionsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readBySectionsCodes(codes,null);
	}
	
	default Collection<ENTITY> readBySectionsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readBySectionsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readBySectionsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readBySectionsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readBySections(Collection<Section> sections,Properties properties) {
		if(CollectionHelper.isEmpty(sections))
			return null;
		return readBySectionsCodes(sections.stream().map(Section::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readBySections(Collection<Section> sections) {
		if(CollectionHelper.isEmpty(sections))
			return null;
		return readBySections(sections,null);
	}
	
	default Collection<ENTITY> readBySections(Properties properties,Section...sections) {
		if(ArrayHelper.isEmpty(sections))
			return null;
		return readBySections(CollectionHelper.listOf(sections),properties);
	}
	
	default Collection<ENTITY> readBySections(Section...sections) {
		if(ArrayHelper.isEmpty(sections))
			return null;
		return readBySections(CollectionHelper.listOf(sections),null);
	}
}
