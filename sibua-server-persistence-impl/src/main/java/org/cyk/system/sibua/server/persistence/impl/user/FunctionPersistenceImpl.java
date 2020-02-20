package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.Function;
import org.cyk.system.sibua.server.persistence.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionPersistenceImpl extends AbstractPersistenceEntityImpl<Function> implements FunctionPersistence,ReadFunctionByUsers,ReadFunctionByAdministrativeUnits,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByUsersIdentifiers,readByAdministrativeUnitsCodes,readWhereBusinessIdentifierOrNameContainsByTypesCategoriesCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(ApplicationScopeLifeCycleListener.isUserEnabled())
			addQueryCollectInstances(readByUsersIdentifiers, "SELECT function FROM Function function WHERE EXISTS (SELECT userFunction FROM UserFunction userFunction WHERE userFunction.function = function AND userFunction.user.identifier IN :usersIdentifiers)  ORDER BY function.code ASC");
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT function FROM Function function WHERE SUBSTRING(function.code,2,8) IN :administrativeUnitsCodes ORDER BY function.code ASC");
		addQueryCollectInstances(readWhereBusinessIdentifierOrNameContainsByTypesCategoriesCodes
				,  "SELECT function FROM Function function WHERE ("
						+ QueryStringHelper.formatTupleFieldLike("function", "code")
						+ " OR "
						+ QueryStringHelper.formatTupleFieldLikeOrTokens("function", "name", 6, LogicalOperator.AND)
						+ ") AND function.type.category.code IN :categoriesCodes"
						+" ORDER BY function.code ASC"); 
				//"SELECT function FROM Function function WHERE function.type.category.code IN :categoriesCodes ORDER BY function.code ASC");
	}
	
	@Override
	public Collection<Function> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
	}
	
	@Override
	public Collection<Function> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Long countByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		ThrowableHelper.throwNotYetImplemented("Function.countByAdministrativeUnitsCodes");
		return null;
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		// TODO Auto-generated method stub
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterFieldByKeys(Function.FIELD_ADMINISTRATIVE_UNITS).getValue()};
			}
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereBusinessIdentifierOrNameContainsByTypesCategoriesCodes)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object codeFieldValue = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field field = queryContext.getFilterFieldByKeys(Function.FIELD_CODE);				
				if(field == null || field.getValue() == null || field.getValue() instanceof String) {
					codeFieldValue = "%"+(field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue()))+"%";
				}				
				List<String> nameFieldValue = queryContext.getFieldValueLikes(Function.FIELD_NAME,7);
				objects = new Object[] {codeFieldValue,nameFieldValue.get(0),nameFieldValue.get(1),nameFieldValue.get(2),nameFieldValue.get(3)
						,nameFieldValue.get(4),nameFieldValue.get(5),nameFieldValue.get(6),queryContext.getFilterFieldByKeys("categoriesCodes").getValue()};
				
				//objects = new Object[] {queryContext.getFilterFieldByKeys("categoriesCodes").getValue()};
			}
			return new Object[]{"code", "%"+objects[0]+"%","name", objects[1],"name1", objects[2],"name2", objects[3],"name3", objects[4]
					,"name4", objects[5],"name5", objects[6],"name6", objects[7],"categoriesCodes",objects[8]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}