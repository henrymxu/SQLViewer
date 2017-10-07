package src.main.java.com.henryxu.SQLViewer;

import java.util.HashMap;
import java.util.Map;

import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.SQLTableData;

public class SQLColumnReference {
	private String instruction;
	private String[] targetData; //0 = table name, 1 = replace column
	private String[] referenceData; //0 = table name, 1 = id column, 2 = data column;
	
	public SQLColumnReference () {
		this.instruction = "Replace";
		initTest();
	}
	
	public void setReferenceColumn (String refTableName, String idColumn, String refColumn) {
		referenceData = new String[] {refTableName, idColumn, refColumn};
	}
	
	public void setTargetColumn (String targetTableName, String targetColumn) {
		targetData = new String[] {targetTableName, targetColumn};
	}
	
	private void initTest () {
		targetData = new String[] {"constructorstandings", "constructorId"};
		referenceData = new String[] {"constructors", "constructorId", "name"};
	}
	
	public SQLTableData[] acreateReferences (SQLTableData[] tableData) {
		int targetTableId = 0;
		
		int referenceIdColumn = 0;
		int referenceTableId = 0;
		int referenceDataColumn = 0;
		
		for (int i = 0; i < tableData.length; i++) {
			if (tableData[i].getTableName().equals(targetData[0])) {
				targetTableId = i;
			} else if (tableData[i].getTableName().equals(referenceData[0])) {
				referenceTableId = i;
			}
		}
		
		for (int i = 0; i < tableData[referenceTableId].getColumnCount(); i++){
			if (tableData[referenceTableId].getColumnName(i).equals(referenceData[1])) {
				referenceIdColumn = i;
			} else if (tableData[referenceTableId].getColumnName(i).equals(referenceData[2])) {
				referenceDataColumn = i;
			}
		}
		Map<Integer, String> referenceMap = getReferenceMap (tableData[referenceTableId], referenceIdColumn, referenceDataColumn);
		for (int i = 0; i < tableData[targetTableId].getColumnCount(); i++) {
			if (tableData[targetTableId].getColumnName(i).equals(targetData[1])) {
				updateTableWithReferences (tableData[targetTableId], referenceMap, i);
			}
		}
		return tableData;
	}
	
	public SQLTableData[] createReferences (SQLTableData[] tableData, boolean singleReplace) {
		Map<Integer, Integer> targetMap = new HashMap<Integer, Integer>();
		int referenceTableId = 0;
		int referenceIdColumn = 0;
		int referenceDataColumn = 0;
		for (int i = 0; i < tableData.length; i++) {
			if (tableData[i].getTableName().equals(referenceData[0])) {
				referenceTableId = i;
				continue;
			}
			for (int j = 0; j < tableData[i].getColumnCount(); j++) {
				if (tableData[i].getColumnName(j).equals(targetData[1])) {
					if (!singleReplace || tableData[i].getTableName().equals(targetData[0])) {
						targetMap.put(i, j);
					}
				}
			}
		}
		for (int i = 0; i < tableData[referenceTableId].getColumnCount(); i++){
			if (tableData[referenceTableId].getColumnName(i).equals(referenceData[1])) {
				referenceIdColumn = i;
			} else if (tableData[referenceTableId].getColumnName(i).equals(referenceData[2])) {
				referenceDataColumn = i;
			}
		}
		
		Map<Integer, String> referenceMap = getReferenceMap (tableData[referenceTableId], referenceIdColumn, referenceDataColumn);
		for (int targetTable : targetMap.keySet()) {
			updateTableWithReferences (tableData[targetTable], referenceMap, targetMap.get(targetTable));
		}
		return tableData;
	}
	
	private Map<Integer, String> getReferenceMap (SQLTableData tableData, int idColumn, int dataColumn) {
		Map<Integer,String> referenceMap = new HashMap<Integer, String>();
		for (int i = 0; i < tableData.getRowCount(); i++) {
			int idKey = Integer.parseInt(tableData.getTableDataAt(i, idColumn));
			referenceMap.put(idKey, tableData.getTableDataAt(i, dataColumn));
		}
		return referenceMap;
	}
	
	private void updateTableWithReferences (SQLTableData tableData, Map<Integer, String> referenceMap, int targetColumn) {
		for (int i = 0; i < tableData.getRowCount(); i++) {
			int keyId = Integer.parseInt(tableData.getTableDataAt(i, targetColumn));
			if (referenceMap.containsKey(keyId)) {
				tableData.setTableDataAt(i, targetColumn, referenceMap.get(keyId));
			}
		}
	}
}
