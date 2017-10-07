package src.main.java.com.henryxu.SQLViewer.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.main.java.com.henryxu.SQLViewer.Views.Listeners.ColumnReferenceSelectedListener;

public class DeclareColumnReferenceView extends JPanel {
	private ColumnSelectorTextField referenceColumnField;
	private ColumnSelectorTextField referenceIdColumnField;
	private ColumnSelectorTextField targetColumnField;
	
	private JButton startButton;
	private JButton saveButton;
	private JButton confirmButton;
	private WindowView parentView;
	
	private ColumnSelectorTextField focusedField;
	
	public DeclareColumnReferenceView (WindowView windowView) {
		super();
		this.parentView = windowView;
		
		referenceColumnField = new ColumnSelectorTextField (this);
		referenceIdColumnField = new ColumnSelectorTextField (this);
		targetColumnField = new ColumnSelectorTextField (this);
		configureTextFields();
		
		startButton = new JButton ("Create Reference");
		saveButton = new JButton ("Save");
		confirmButton = new JButton ("---->");
		configureStartButton ();
		configureConfirmButton ();
		configureSaveButton ();
		
		resetViews ();
		this.setLayout(new FlowLayout()); 
		this.add(startButton);
		this.add(referenceColumnField);
		this.add(referenceIdColumnField);
		this.add(confirmButton);
		this.add(targetColumnField);
		this.add(saveButton);	
	}

	public String getReferenceColumnName() {
		return referenceColumnField.getText();
	}
	
	public String getReferenceIdColumnName() {
		return referenceIdColumnField.getText();
	}

	public String getTargetColumnName() {
		return targetColumnField.getText();
	}
	
	public WindowView getParentView () {
		return parentView;
	}
	
	private void configureStartButton () {
		startButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				referenceColumnField.setEnabled(true);
				referenceColumnField.grabFocus();
				
				referenceIdColumnField.setEnabled(true);
				confirmButton.setEnabled(true);
				startButton.removeActionListener(this);
				startButton.setText("Cancel");
				startButton.addActionListener (new ActionListener () {
					@Override
					public void actionPerformed(ActionEvent e) {
						startButton.setText("Create Reference");
						resetViews();
						configureStartButton();
					}
				});
			}
		});
	}
	
	private void configureConfirmButton () {
		confirmButton.setEnabled(false);
		confirmButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isNullReferenceFields()) {
					return;
				}
				parentView.createColumnReference(getReferenceColumnName(), getReferenceIdColumnName());
				saveButton.setEnabled(true);
				confirmButton.setEnabled(false);
				referenceColumnField.setEnabled(false);
				referenceIdColumnField.setEnabled(false);
				targetColumnField.setEnabled(true);
				parentView.toggleTabSelectable(true);
			}
			
		});
	}
	
	private boolean isNullReferenceFields () {
		return getReferenceColumnName().isEmpty() && getReferenceIdColumnName().isEmpty();
	}
	
	private void configureSaveButton () {
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentView.createColumnTarget(getTargetColumnName());
				configureTextFields();
				resetViews();
			}
		});
	}
	
	private void configureTextFields() {
		referenceColumnField.setDefaultText("Reference Values");
		referenceIdColumnField.setDefaultText("Reference Keys");
		targetColumnField.setDefaultText("Target Values");
	}
	
	private void resetViews () {
		referenceColumnField.setEnabled(false);
		referenceIdColumnField.setEnabled(false);
		targetColumnField.setEnabled(false);
		startButton.setEnabled(true);
		confirmButton.setEnabled(false);
		saveButton.setEnabled(false);
		parentView.toggleTabSelectable(true);
		configureTextFields();
	}
	
	public void setCurrentTableCallback (TableView tableView) {
		tableView.setColumnSelectedCallback(columnSelectedListener);
	}
	
	private ColumnReferenceSelectedListener columnSelectedListener = new ColumnReferenceSelectedListener () {
		@Override
		public void tableColumnSelected(String columnName) {
			if (focusedField != null) {
				focusedField.setText(columnName);
			}
		}
	};
	
	public void setCurrentFocus (ColumnSelectorTextField currentField) {
		focusedField = currentField;
		if (referenceIdColumnField.getText().isEmpty() && !referenceColumnField.getText().isEmpty() && confirmButton.isEnabled()) {
			referenceIdColumnField.grabFocus();
			focusedField = referenceIdColumnField;
		}
		parentView.toggleTabSelectable(focusedField != null && !focusedField.getText().isEmpty());
	}
	
	public void setSelectedColumnName (String columnName) {
		focusedField.setText(columnName);
	}
}
