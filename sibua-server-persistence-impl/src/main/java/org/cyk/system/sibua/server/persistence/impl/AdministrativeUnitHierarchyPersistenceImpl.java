package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class AdministrativeUnitHierarchyPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitHierarchy> implements AdministrativeUnitHierarchyPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByParentCodeByChildCode,readWhereIsChildByChildrenCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByParentCodeByChildCode, "SELECT administrativeUnitHierarchy FROM AdministrativeUnitHierarchy administrativeUnitHierarchy "
				+ "WHERE administrativeUnitHierarchy.parent.code = :parentCode AND administrativeUnitHierarchy.child.code = :childCode");
		addQueryCollectInstances(readWhereIsChildByChildrenCodes, "SELECT administrativeUnitHierarchy FROM AdministrativeUnitHierarchy administrativeUnitHierarchy "
				+ "WHERE administrativeUnitHierarchy.child.code = :childrenCodes");
	}
	
	@Override
	public AdministrativeUnitHierarchy readByParentCodeByChildCode(String parentCode, String childCode,Properties properties) {
		if(StringHelper.isBlank(parentCode) || StringHelper.isBlank(childCode))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByParentCodeByChildCode);
		return __readOne__(properties, ____getQueryParameters____(properties,parentCode,childCode));
	}
	
	@Override
	public Collection<AdministrativeUnitHierarchy> readWhereIsChildByChildrenCodes(Collection<String> codes,Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readWhereIsChildByChildrenCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}

	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentCodeByChildCode)) {
			return new Object[]{"parentCode",objects[0],"childCode",objects[1]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereIsChildByChildrenCodes)) {
			return new Object[]{"childrenCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}

	
}