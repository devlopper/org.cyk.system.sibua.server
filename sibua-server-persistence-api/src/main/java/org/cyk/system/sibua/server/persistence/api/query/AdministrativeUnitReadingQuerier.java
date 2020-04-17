package org.cyk.system.sibua.server.persistence.api.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.Querier;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryValueBuilder;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.value.Value;

public interface AdministrativeUnitReadingQuerier extends Querier {

	Collection<AdministrativeUnit> readMany(QueryExecutorArguments arguments);
	Long count(QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements AdministrativeUnitReadingQuerier,Serializable {
		@Override
		public Collection<AdministrativeUnit> readMany(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);	
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_READ_VIEW_02.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);	
			return QueryExecutor.getInstance().executeReadMany(AdministrativeUnit.class, arguments);
		}
		
		@Override
		public Long count(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_COUNT_VIEW_02.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);
			return QueryExecutor.getInstance().executeCount(arguments);
		}
		
		private static void prepare(QueryExecutorArguments arguments) {
			Filter newFilter = new Filter();
			if(QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()) || QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier())) {
				List<String> administrativeUnitTokens = arguments.getFilterFieldValueLikes(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, 5);			
				newFilter.addField(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, administrativeUnitTokens.get(0));			
				newFilter.addFieldsLikesByPrefix("administrativeUnitName", administrativeUnitTokens);			
				newFilter.addFieldsTransformedToLike(arguments.getFilter(), AdministrativeUnit.FIELD_SECTION,AdministrativeUnit.FIELD_SERVICE_GROUP
						,AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_LOCALISATION);			
			}
			if(QUERY_IDENTIFIER_READ_VIEW_02.equals(arguments.getQuery().getIdentifier()) || QUERY_IDENTIFIER_COUNT_VIEW_02.equals(arguments.getQuery().getIdentifier())) {
				List<String> administrativeUnitTokens = arguments.getFilterFieldValueLikes(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, 5);			
				newFilter.addField(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, administrativeUnitTokens.get(0));			
				newFilter.addFieldsLikesByPrefix("administrativeUnitName", administrativeUnitTokens);			
				newFilter.addFieldsTransformedToLike(arguments.getFilter(), AdministrativeUnit.FIELD_SECTION,AdministrativeUnit.FIELD_SERVICE_GROUP
						,AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_LOCALISATION);	
				
			}
			arguments.setFilter(newFilter);
		}
	}
	
	/**/
	
	String QUERY_NAME_READ_VIEW_01 = "read.view.01";
	String QUERY_NAME_COUNT_VIEW_01 = "count.view.01";
	String QUERY_NAME_READ_VIEW_02 = "read.view.02";
	String QUERY_NAME_COUNT_VIEW_02 = "count.view.02";
	
	String QUERY_IDENTIFIER_READ_VIEW_01 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class, QUERY_NAME_READ_VIEW_01);
	String QUERY_IDENTIFIER_COUNT_VIEW_01 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class, QUERY_NAME_COUNT_VIEW_01);
	String QUERY_IDENTIFIER_READ_VIEW_02 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class, QUERY_NAME_READ_VIEW_02);
	String QUERY_IDENTIFIER_COUNT_VIEW_02 = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class, QUERY_NAME_COUNT_VIEW_02);
	
	String QUERY_VALUE_READ_VIEW_01 = "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
			+ "WHERE "
			+ "("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "code","administrativeUnit") + " OR " + QueryStringHelper.formatTupleFieldLikeOrTokens("administrativeUnit", "name","administrativeUnitName",4,LogicalOperator.AND)+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "section.code","section") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "section.name","section")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "serviceGroup.code","serviceGroup") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "serviceGroup.name","serviceGroup")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "functionalClassification.code","functionalClassification") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "functionalClassification.name","functionalClassification")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "localisation.code","localisation") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "localisation.name","localisation")+")"
			+ "ORDER BY administrativeUnit.code ASC";
	
	String QUERY_VALUE_READ_VIEW_02_WHERE = " WHERE "
			+ "("+QueryStringHelper.formatTupleFieldLike("t", "code","administrativeUnit") + " OR " + QueryStringHelper.formatTupleFieldLikeOrTokens("t", "name","administrativeUnitName",4,LogicalOperator.AND)+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "section.code","section") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "section.name","section")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "serviceGroup.code","serviceGroup") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "serviceGroup.name","serviceGroup")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "functionalClassification.code","functionalClassification") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "functionalClassification.name","functionalClassification")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "localisation.code","localisation") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "localisation.name","localisation")+")";				
	
	String QUERY_VALUE_READ_VIEW_02 = "SELECT t.identifier,"
			+QueryValueBuilder.deriveConcatsCodeAndNameFromTuplesNames("t",AdministrativeUnit.FIELD_SECTION,AdministrativeUnit.FIELD_SERVICE_GROUP
					,AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_LOCALISATION)			
			+ " FROM AdministrativeUnit t "
			+QueryValueBuilder.deriveLeftJoinsFromFieldsNames("t", AdministrativeUnit.FIELD_SECTION,AdministrativeUnit.FIELD_SERVICE_GROUP
					,AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_LOCALISATION)			
			+ QUERY_VALUE_READ_VIEW_02_WHERE	
			+ " ORDER BY t.code ASC";
	
	String QUERY_VALUE_COUNT_VIEW_02 = "SELECT COUNT(t.identifier) FROM AdministrativeUnit t "+ QUERY_VALUE_READ_VIEW_02_WHERE;
	
	/**/
	
	static AdministrativeUnitReadingQuerier getInstance() {
		return Helper.getInstance(AdministrativeUnitReadingQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}