package src.main.java.com.henryxu.SQLViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.MySQLDatabaseHelper;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.QueryHelper;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.SQLTableData;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.Interfaces.ConnectionHelper;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.Interfaces.DatabaseHelper;
import src.main.java.com.henryxu.SQLViewer.Views.TableView;
import src.main.java.com.henryxu.SQLViewer.Views.WindowView;
import src.main.java.com.henryxu.SQLViewer.Views.Listeners.SQLCreateColumnReferenceListener;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.MySQLConnectionHelper;

public class main {
	static SQLTableData [] data = null;
	static WindowView windowView = new WindowView();
	
	public static void main(String[] args) {
		
		ConnectionHelper sqlHelper = new MySQLConnectionHelper();
		Connection dbConnection = sqlHelper.getConnection();
		
		try {
			DatabaseHelper dbHelper = new MySQLDatabaseHelper (dbConnection);
			String [] tableNames = dbHelper.getTableNames();
			data = new SQLTableData [tableNames.length];
			for (int i = 0; i < tableNames.length; i++) {
				String query = QueryHelper.selectFromTable(tableNames [i]);
				ResultSet queryResult = dbHelper.runSQLQuery(query);
				data [i] = new SQLTableData (queryResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		configureSQLColumnListener ();
		windowView.setColumnReferenceListener(columnReferenceListener);
		displayData();
	}
	
	public static void displayData () {
		for (int i = 0; i < data.length; i++) {
			SQLTableModel tableModel = new SQLTableModel (data[i]);
			TableView tableView = new TableView (tableModel);
			windowView.attachTab(tableView.getTableName(), tableView);
		}
		windowView.show();
	}
	
	public static void notifyAllTables () {
		for (int i = 0; i < data.length; i++) {
			windowView.getTableViewAt(i).updateTableModel(data[i]);
		}
	}
	
	private static SQLCreateColumnReferenceListener columnReferenceListener;
	private static SQLColumnReference columnReference;
	public static void configureSQLColumnListener () {
		columnReferenceListener = new SQLCreateColumnReferenceListener () {

			@Override
			public void onReferenceSelected(String referenceTable, String refColumn, String idColumn) {
				columnReference = new SQLColumnReference ();
				columnReference.setReferenceColumn(referenceTable, idColumn, refColumn);
			}

			@Override
			public void onTargetSelected(String targetTable, String targetColumn) {
				columnReference.setTargetColumn(targetTable, targetColumn);
				data = columnReference.createReferences(data, false);
				notifyAllTables();
			}
		};
	}

}
