package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SQLUtils {
	
	public static int getRowCount (ResultSet resultSet) {
		try {
			resultSet.last();
			int rowCount = resultSet.getRow();
			resultSet.beforeFirst();
			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static boolean isColumnTypeNumeric (int cType) {
		return cType == Types.BIGINT || cType == Types.DECIMAL || cType == Types.DOUBLE || cType == Types.FLOAT || cType == Types.INTEGER || cType == Types.NUMERIC
				|| cType == Types.TIMESTAMP || cType == Types.TINYINT || cType == Types.REAL;
	}
	
	public static boolean sortNumeric (Double n1, Double n2, boolean isGreater, boolean isLesser) {
		if ((isGreater && !isLesser) && n1 < n2) {
			return true;
		} else if (!isGreater || isLesser) {
			if (n1 > n2) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean sortString (String s1, String s2, boolean isGreater, boolean isLesser) {
		if ((isGreater && !isLesser) && s1.toLowerCase().compareTo(s2.toLowerCase()) < 0) {
			return true;
		} else if (!isGreater || isLesser) {
			if (s1.toLowerCase().compareTo(s2.toLowerCase()) > 0) {
				return true;
			}
		}
		return false;
	}
}
