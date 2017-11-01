package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;

public class QueryHelper {
	
	public static String selectFromTable (String tableName, QueryFilter...queryFilters) {
		String filterQuery = "SELECT * FROM " + tableName;
		for (QueryFilter queryFilter : queryFilters) {
			filterQuery += (queryFilters.length > 1) ? " AND " : " WHERE ";
			filterQuery += queryFilter.getFilter();
		}
		System.out.println (filterQuery);
		return filterQuery;
	}
	
}
