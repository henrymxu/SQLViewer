package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseHelper {

	String[] getTableNames() throws SQLException;

	ResultSet runSQLQuery(String query) throws SQLException;

}