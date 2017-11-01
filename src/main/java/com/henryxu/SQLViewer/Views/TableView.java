package src.main.java.com.henryxu.SQLViewer.Views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import src.main.java.com.henryxu.SQLViewer.SQLTableModel;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.SQLTableData;
import src.main.java.com.henryxu.SQLViewer.Views.Listeners.ColumnReferenceSelectedListener;

public class TableView extends JPanel {
	private SQLTableModel tableModel;
	private JTable jTable;
	private JScrollPane scrollPane;
	private JTextField resultsCountField;
	
	private ColumnReferenceSelectedListener columnSelectedCallback = null;
	
	public TableView (SQLTableModel tableModel) {
		super();
		this.tableModel = tableModel;
		
		jTable = new JTable (tableModel);
		configureTable ();
		
		scrollPane = new JScrollPane();
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setViewportView(jTable);
		
		resultsCountField = new JTextField ();
		resultsCountField.setEditable(false);
		setResultsCountField();
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(resultsCountField, BorderLayout.PAGE_END);
		
		jTable.getTableHeader().addMouseListener(selectHeaderMouseAdapter);
	}
	
	public void setColumnSelectedCallback(ColumnReferenceSelectedListener columnSelectedCallback) {
		this.columnSelectedCallback = columnSelectedCallback;
	}
	
	public String getTableName () {
		return tableModel.getTableName();
	}
	
	public void searchTableData (String text) {
		tableModel.searchTableData(text);
		setResultsCountField();
	}
	
	public void setResultsCountField () {
		String results = "%s result(s)";
		resultsCountField.setText(String.format(results, tableModel.getRowCount()));
	}
	
	private void configureTable () {
		jTable.setFillsViewportHeight(true);
		configureTableColumnWidth ();
	}
	
	public void updateTableModel (SQLTableData tableData) {
		tableModel.setTableData(tableData);
		tableModel.fireTableDataChanged();
	}
	
	private void configureTableColumnWidth () {
		final TableColumnModel columnModel = jTable.getColumnModel();
	    for (int i = 0; i < jTable.getColumnCount(); i++) {
	        int width = 10;
	        for (int j = 0; j < jTable.getRowCount(); j++) {
	            TableCellRenderer renderer = jTable.getCellRenderer(j, i);
	            Component comp = jTable.prepareRenderer(renderer, j, i);
	            width = Math.max(comp.getPreferredSize().width + 1 , width);
	        }
	        if(width > 300) {
	            width = 300;
	        }
	        columnModel.getColumn(i).setPreferredWidth(width);
	    }
	}
	
	private MouseAdapter selectHeaderMouseAdapter = new MouseAdapter (){
		@Override
		public void mouseClicked(MouseEvent e) {
			int col = jTable.columnAtPoint(e.getPoint());
			String name = jTable.getColumnName(col);
			System.out.println("Column index " + col + " " + name);
			if (columnSelectedCallback.tableColumnSelected(name)) {
				TableView.this.grabFocus();
			} else {
				tableModel.sortTableData(col);
			}
		}
	};
	
}
