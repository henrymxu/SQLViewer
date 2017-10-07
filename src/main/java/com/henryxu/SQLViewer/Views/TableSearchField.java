package src.main.java.com.henryxu.SQLViewer.Views;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class TableSearchField extends JTextField {
	private TableView parentView;
	
	public TableSearchField () {
		super();
		configureSearchField();
	}
	
	public void setCurrentTableView (TableView tableView) {
		this.parentView = tableView;
	}
	
	private void configureSearchField () {
		this.setToolTipText("Search");
		this.addKeyListener(new KeyListener () {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int key = arg0.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					TableSearchField.this.search();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//TableSearchField.this.search();
			}
		});
	}

	private void search () {
		String searchText = TableSearchField.this.getText();
		parentView.searchTableData (searchText);
	}
	
}
