package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.Interfaces.DatabaseHelper;

public class MySQLDatabaseHelper implements DatabaseHelper {
	private static final String tableCatlog = null;
	private static final String tableSchemaPattern = null;
	private static final String tableNamePattern = "%";
	private static final String[] tableTypes = null;
	
	private DatabaseMetaData dbMetaData;
	private Connection dbConnection;
	
	public MySQLDatabaseHelper (Connection dbConnection) throws SQLException {
		this.dbConnection = dbConnection;
		dbMetaData = dbConnection.getMetaData();
		//System.out.println(dbMetaData.get);
	}
	
	/* (non-Javadoc)
	 * @see src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.DatabaseHelper#getTableNames()
	 */
	@Override
	public String[] getTableNames () throws SQLException {
		ResultSet resultSet = dbMetaData.getTables(tableCatlog, tableSchemaPattern, tableNamePattern, tableTypes);
		String[] tableNames = new String [SQLUtils.getRowCount(resultSet)];
		for (int i = 0; i < tableNames.length; i++) {
			resultSet.next();
			tableNames [i] = resultSet.getString(3);
		}
		return tableNames;
	}
	
	/* (non-Javadoc)
	 * @see src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.DatabaseHelper#runSQLQuery(java.lang.String)
	 */
	@Override
	public ResultSet runSQLQuery (String query) throws SQLException {
		Statement st = dbConnection.createStatement ();
		return st.executeQuery(query);
	}
	
}
