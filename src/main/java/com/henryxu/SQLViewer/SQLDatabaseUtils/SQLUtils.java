package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
