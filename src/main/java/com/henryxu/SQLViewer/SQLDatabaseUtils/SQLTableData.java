package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SQLTableData {
	private String tableName;
	private int [] columnTypes;
	private String[] columnNames;
	
	private String[][] tableData;
	private String[][] displayData;
	
	public SQLTableData (ResultSet resultSet) {
		tableData = SQLTableData.createDataFromSet(resultSet);
		columnNames = (SQLTableData.getColumnNames(resultSet));
		columnTypes = (SQLTableData.getColumnTypes(resultSet));
		tableName = SQLTableData.getTableName(resultSet);
		
		setDisplayedData (tableData);
	}
	
	public void setTableDataAt (int row, int column, String value) {
		tableData[row] [column] = value;
	}
	
	private void setDisplayedData (String[][] data) {
		displayData = data;
	}
	
	public String getTableName () {
		return tableName;
	}
	
	public String getTableDataAt (int row, int column) {
		return displayData [row][column];
	}
	
	public String getColumnName (int index) {
		return columnNames[index];
	}
	
	public int getRowCount () {
		return displayData.length;
	}
	
	public int getColumnCount () {
		return columnTypes.length;
	}
	
	public void searchTableData (String text) {
		if (text.trim().length() == 0) {
			setDisplayedData (tableData);
			return;
		}
		
		List<Integer> matchingRows = new ArrayList<Integer>();
		for (int i = 0; i < tableData.length; i++) {
			for (int j = 0; j < tableData[0].length; j++) {
				if (tableData[i][j] != null && tableData[i][j].contains(text)) {
					matchingRows.add(i);
					break;
				}
			}
		}
		String [][] matchingData = new String [matchingRows.size()][tableData[0].length];
		int index = 0;
		for (int matchingRow : matchingRows) {
			matchingData[index] = tableData[matchingRow];
			index++;
		}
		setDisplayedData(matchingData);
	}
	
	public void sortTableData (int index) {
		boolean isSortedGreater = true;
		boolean isSortedLesser = true;
		boolean isNumeric = SQLUtils.isColumnTypeNumeric(columnTypes[index]);
		for (int j = 0; j < displayData.length - 1; j++) {
			if (isNumeric) {
				isSortedGreater = isSortedGreater & Double.parseDouble(displayData[j][index]) <= Double.parseDouble(displayData[j+1][index]);
				isSortedLesser = isSortedLesser & Double.parseDouble(displayData[j][index]) >= Double.parseDouble(displayData[j+1][index]);
			} else {
				isSortedGreater = isSortedGreater & displayData[j][index].toLowerCase().compareTo(displayData[j+1][index].toLowerCase()) <= 0; 
				isSortedLesser = isSortedLesser & displayData[j][index].toLowerCase().compareTo(displayData[j+1][index].toLowerCase()) >= 0;
			}
		}
		String [][] sortedData = displayData;
		for (int i = 0; i < sortedData.length; i++) {
			for (int j = 0; j < sortedData.length - 1; j++) {
					if (isNumeric) {
						if (SQLUtils.sortNumeric(Double.parseDouble(displayData[j][index]), Double.parseDouble(displayData[j+1][index]), isSortedGreater, isSortedLesser)) {
							String[] tempRow = sortedData[j];
							sortedData[j] = sortedData[j+1];
							sortedData[j+1] = tempRow;
						}
					} else {
						//if (sortedData[j][index].toLowerCase().compareTo(sortedData[j+1][index].toLowerCase()) > 0) {
						if (SQLUtils.sortString(sortedData[j][index], sortedData[j+1][index], isSortedGreater, isSortedLesser)) {
							String[] tempRow = sortedData[j];
							sortedData[j] = sortedData[j+1];
							sortedData[j+1] = tempRow;
						}
					}
				}
			}
		setDisplayedData(sortedData);
	}
	
	public void logData () {
		String data = new String();
		for (int i = 0; i < tableData[0].length; i++) {
			data += columnNames [i] + '\t';
		}
		data += '\n';
		for (int i = 0; i < tableData.length; i++) {
			for (int j = 0; j < tableData[0].length; j++) {
				data += tableData[i][j] + '\t';
			}
			data += '\n';
		}
		System.out.println(data);
	}
	
	private static String[][] createDataFromSet (ResultSet resultSet) {
		try {
			resultSet.beforeFirst();
			int columnCount = resultSet.getMetaData().getColumnCount();
			int rowCount = SQLUtils.getRowCount(resultSet);
			String [][] data = new String [rowCount][columnCount];
			for (int i = 0; i < rowCount; i++) {
				resultSet.next();
				for (int j = 0; j < columnCount; j++) {
					data[i][j] = resultSet.getString(j + 1);
				}
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String[] getColumnNames (ResultSet resultSet) {
		String[] columnNames = null;
		try {
			resultSet.first();
			columnNames = new String [resultSet.getMetaData().getColumnCount()];
			for (int i = 0; i < columnNames.length; i++) {
				columnNames [i] = resultSet.getMetaData().getColumnLabel(i + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNames;
	}
	
	private static int[] getColumnTypes (ResultSet resultSet) {
		int[] columnTypes = null;
		try {
			resultSet.first();
			columnTypes = new int [resultSet.getMetaData().getColumnCount()];
			for (int i = 0; i < columnTypes.length; i++) {
				columnTypes [i] = resultSet.getMetaData().getColumnType(i + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnTypes;
	}
	
	private static String getTableName (ResultSet resultSet) {
		try {
			return resultSet.getMetaData().getTableName(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "N/A";
	}
}
