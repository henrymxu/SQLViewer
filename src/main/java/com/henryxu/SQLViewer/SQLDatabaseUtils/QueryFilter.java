package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;

public class QueryFilter {
	private String column;
	private String comparator;
	private String value;
	
	private String operator;
	
	public QueryFilter (String column, String comparator, String value) {
		this.column = column;
		this.comparator = comparator;
		this.value = value;
	}
	
	public String getFilter () {
		return column + " " + comparator + " " + value;
	}

	public String getColumn() {
		return column;
	}

	public String getComparator() {
		return comparator;
	}

	public String getValue() {
		return value;
	}

	public String getOperator() {
		return operator;
	}
	
}
