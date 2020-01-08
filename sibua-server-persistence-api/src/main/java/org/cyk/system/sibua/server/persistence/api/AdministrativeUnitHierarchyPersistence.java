package org.cyk.system.sibua.server.persistence.api;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface AdministrativeUnitHierarchyPersistence extends PersistenceEntity<AdministrativeUnitHierarchy> {

	Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildrenCodes(Collection<String> codes,Properties properties);
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildrenCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readWhereIsChildByChildrenCodes(codes,null);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildrenCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readWhereIsChildByChildrenCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildrenCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readWhereIsChildByChildrenCodes(null,codes);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildren(Collection<AdministrativeUnit> administrativeUnits,Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsChildByChildrenCodes(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList()),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildren(Collection<AdministrativeUnit> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsChildByChildren(administrativeUnits,null);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildren(Properties properties,AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsChildByChildren(CollectionHelper.listOf(administrativeUnits),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildren(AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsChildByChildren(null,administrativeUnits);
	}
	
	/**/
	
	Collection<AdministrativeUnitHierarchy> readWhereIsParentByParentsCodes(Collection<String> codes,Properties properties);
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParentsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readWhereIsParentByParentsCodes(codes,null);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParentsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readWhereIsParentByParentsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParentsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readWhereIsParentByParentsCodes(null,codes);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParents(Collection<AdministrativeUnit> administrativeUnits,Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsParentByParentsCodes(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList()),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParents(Collection<AdministrativeUnit> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsParentByParents(administrativeUnits,null);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParents(Properties properties,AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsParentByParents(CollectionHelper.listOf(administrativeUnits),properties);
	}
	
	default Collection<AdministrativeUnitHierarchy> readWhereIsParentByParents(AdministrativeUnit...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readWhereIsParentByParents(null,administrativeUnits);
	}
	
	/**/
	
	AdministrativeUnitHierarchy readByParentCodeByChildCode(String parentCode,String childCode,Properties properties);
	
	default AdministrativeUnitHierarchy readByParentCodeByChildCode(String parentCode,String childCode) {
		if(StringHelper.isBlank(parentCode) || StringHelper.isBlank(childCode))
			return null;
		return readByParentCodeByChildCode(parentCode, childCode,null);
	}
	
	default AdministrativeUnitHierarchy readByParentByChild(AdministrativeUnit parent,AdministrativeUnit child,Properties properties) {
		if(parent == null || child == null)
			return null;
		return readByParentCodeByChildCode(parent.getCode(), child.getCode(),properties);
	}
	
	default AdministrativeUnitHierarchy readByParentByChild(AdministrativeUnit parent,AdministrativeUnit child) {
		if(parent == null || child == null)
			return null;
		return readByParentByChild(parent, child,null);
	}
	
}
