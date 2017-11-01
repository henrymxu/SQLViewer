package src.main.java.com.henryxu.SQLViewer;

import javax.swing.table.AbstractTableModel;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.SQLTableData;

public class SQLTableModel extends AbstractTableModel {
	private SQLTableData tableData;
	
	public SQLTableModel (SQLTableData tableData) {
		super();
		this.tableData = tableData;
	}
	
	public void setTableData (SQLTableData tableData) {
		this.tableData = tableData;
	}
	
	public void searchTableData (String text) {
		tableData.searchTableData(text);
		this.fireTableDataChanged();
	}
	
	public void sortTableData (int columnIndex) {
		tableData.sortTableData(columnIndex);
		this.fireTableDataChanged();
	}

	public String getTableName () {
		return tableData.getTableName();
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return getValueAt(0, arg0) == null ? String.class : getValueAt(0, arg0).getClass();
	}

	@Override
	public int getColumnCount() {
		return tableData.getColumnCount();
	}

	@Override
	public String getColumnName(int arg0) {
		return tableData.getColumnName(arg0);
	}

	@Override
	public int getRowCount() {
		return tableData.getRowCount();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return tableData.getTableDataAt(arg0, arg1);
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		System.out.println("setValueAt");
	}

}
