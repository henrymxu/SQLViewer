package src.main.java.com.henryxu.SQLViewer.Views;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.main.java.com.henryxu.SQLViewer.Views.Listeners.SQLCreateColumnReferenceListener;

public class WindowView {
	private JFrame mainFrame;
	private JTabbedPane tabbedPane;
	private TableSearchField searchField;
	private DeclareColumnReferenceView referenceView;
	
	private SQLCreateColumnReferenceListener columnReferenceCallback;
	
	public WindowView () {
		mainFrame = new JFrame ();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane ();
		configureTabbedPane();
		mainFrame.add(tabbedPane, BorderLayout.CENTER);
		
		searchField = new TableSearchField ();
		
		referenceView = new DeclareColumnReferenceView(this);
		mainFrame.add(referenceView, BorderLayout.PAGE_END);
		mainFrame.add(searchField, BorderLayout.PAGE_START);
	}

	public void setTitle (String title) {
		mainFrame.setTitle(title);
	}
	
	public void attachComponent (JComponent component) {
		tabbedPane.add(component);
	}
	
	public void show () {
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	
	public void attachTab (String title, JComponent component) {
		tabbedPane.addTab(title, component);
	}
	
	public TableView getCurrentTableView () {
		return (TableView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
	}
	
	public TableView getTableViewAt (int index) {
		return (TableView) tabbedPane.getComponentAt(index);
	}
	
	public void toggleTabSelectable (boolean isSelectable) {
		tabbedPane.setEnabled(isSelectable);
	}
	
	private void configureTabbedPane () {
		tabbedPane.addChangeListener(new ChangeListener () {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int currentTab = tabbedPane.getSelectedIndex();
				TableView tableView = (TableView) tabbedPane.getComponentAt(currentTab);
				referenceView.setCurrentTableCallback(tableView);
				searchField.setCurrentTableView(tableView);
			}
		});
	}
	
	public void createColumnReference (String referenceColumn, String idColumn) {
		columnReferenceCallback.onReferenceSelected(getCurrentTableView().getTableName(), referenceColumn, idColumn);
	}
	
	public void createColumnTarget (String targetColumn) {
		columnReferenceCallback.onTargetSelected(getCurrentTableView().getTableName(), targetColumn);
	}
	
	public void setColumnReferenceListener (SQLCreateColumnReferenceListener listener) {
		columnReferenceCallback = listener;
	}
}
