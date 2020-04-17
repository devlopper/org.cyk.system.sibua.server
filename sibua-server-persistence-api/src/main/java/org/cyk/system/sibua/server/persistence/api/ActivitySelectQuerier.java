package org.cyk.system.sibua.server.persistence.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.Querier;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryValueBuilder;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.value.Value;

public interface ActivitySelectQuerier extends Querier {

	Collection<Activity> readMany(QueryExecutorArguments arguments);
	Long count(QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ActivitySelectQuerier,Serializable {
		@Override
		public Collection<Activity> readMany(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);	
			return QueryExecutor.getInstance().executeReadMany(Activity.class, arguments);
		}
		
		@Override
		public Long count(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				prepare(arguments);
			return QueryExecutor.getInstance().executeCount(arguments);
		}
		
		private static void prepare(QueryExecutorArguments arguments) {
			Filter newFilter = new Filter();
			if(QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()) || QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier())) {
				/*List<String> administrativeUnitTokens = arguments.getFilterFieldValueLikes(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, 5);			
				newFilter.addField(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT, administrativeUnitTokens.get(0));			
				newFilter.addFieldsLikesByPrefix("administrativeUnitName", administrativeUnitTokens);			
				newFilter.addFieldsTransformedToLike(arguments.getFilter(), AdministrativeUnit.FIELD_SECTION,AdministrativeUnit.FIELD_SERVICE_GROUP
						,AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,AdministrativeUnit.FIELD_LOCALISATION);	
				*/
			}
			arguments.setFilter(newFilter);
		}
	}
	
	/**/
	
	String QUERY_NAME_READ_VIEW_01 = "read.view.01";
	String QUERY_NAME_COUNT_VIEW_01 = "count.view.01";
	
	String QUERY_IDENTIFIER_READ_VIEW_01 = QueryIdentifierBuilder.getInstance().build(Activity.class, QUERY_NAME_READ_VIEW_01);
	String QUERY_IDENTIFIER_COUNT_VIEW_01 = QueryIdentifierBuilder.getInstance().build(Activity.class, QUERY_NAME_COUNT_VIEW_01);
	
	String QUERY_VALUE_READ_VIEW_01_WHERE = " WHERE "
			+ "("+QueryStringHelper.formatTupleFieldLike("t", "code","activity") + " OR " + QueryStringHelper.formatTupleFieldLikeOrTokens("t", "name","activityName",4,LogicalOperator.AND)+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "action.code","action") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "action.name","action")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "action.program.code","program") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "action.program.name","program")+")"
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "action.program.section.code","section") + " OR " + QueryStringHelper.formatTupleFieldLike("t", "action.program.section.name","section")+")"
			
			+ " AND (EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = t AND "
			+ " (LOWER(administrativeUnitActivity.administrativeUnit.code) LIKE LOWER(:administrativeUnit) OR administrativeUnitActivity.administrativeUnit.name LIKE LOWER(:administrativeUnit) "
			+ "))) "
			
			+ " AND (EXISTS (SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity = t AND "
			+ " (LOWER(administrativeUnitActivity.administrativeUnitBeneficiaire.code) LIKE LOWER(:administrativeUnitBeneficiaire) OR administrativeUnitActivity.administrativeUnitBeneficiaire.name LIKE LOWER(:administrativeUnitBeneficiaire)"
			+ "))) "
			
			+ " AND ("+QueryStringHelper.formatTupleFieldLike("t", "catAtvCode")+")";
			
	String QUERY_VALUE_READ_VIEW_01 = "SELECT t.identifier,"
			+QueryValueBuilder.deriveConcatsCodeAndNameFromTuplesNames("t",Activity.FIELD_ACTION,Action.FIELD_PROGRAM,Program.FIELD_SECTION,Activity.FIELD_FUNCTION_TYPE
					,"beneficiary","manager")+",t.catAtvCode"				
			+ " FROM Activity t "
			+QueryValueBuilder.deriveLeftJoinsFromFieldsNames("t", Activity.FIELD_ACTION,FieldHelper.join(Activity.FIELD_ACTION,Action.FIELD_PROGRAM)
					,FieldHelper.join(Activity.FIELD_ACTION,Action.FIELD_PROGRAM,Program.FIELD_SECTION),Activity.FIELD_FUNCTION_TYPE)
			+" LEFT JOIN AdministrativeUnitActivity administrativeUnitActivity ON administrativeUnitActivity.activity.identifier = t.identifier"
			+" LEFT JOIN AdministrativeUnit manager ON manager.identifier = administrativeUnitActivity.administrativeUnit.identifier"
			+" LEFT JOIN AdministrativeUnit beneficiary ON beneficiary.identifier = administrativeUnitActivity.administrativeUnitBeneficiaire.identifier"
			//+ QUERY_VALUE_READ_VIEW_01_WHERE	
			+ " ORDER BY t.code ASC";
	
	String QUERY_VALUE_COUNT_VIEW_01 = "SELECT COUNT(t.identifier) FROM Activity t "/*+ QUERY_VALUE_READ_VIEW_01_WHERE*/;
	
	/**/
	
	static ActivitySelectQuerier getInstance() {
		return Helper.getInstance(ActivitySelectQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}