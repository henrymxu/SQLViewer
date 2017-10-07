package src.main.java.com.henryxu.SQLViewer.Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import src.main.java.com.henryxu.SQLViewer.Views.Listeners.ColumnReferenceSelectedListener;

public class ColumnSelectorTextField extends JTextField {
	
	public ColumnSelectorTextField (DeclareColumnReferenceView parentView) {
		super();
		this.setEditable(false);
		this.setHighlighter(null);
		this.setPreferredSize(new Dimension (600, 25));
		addFocusListener(new FocusListener () {
			@Override
			public void focusGained (FocusEvent arg0) {
				parentView.setCurrentFocus(ColumnSelectorTextField.this);
				ColumnSelectorTextField.this.setBackground(Color.GREEN);
			}

			@Override
			public void focusLost(FocusEvent e) {
				parentView.setCurrentFocus(null);
				ColumnSelectorTextField.this.setBackground(Color.WHITE);
			}
		});
	}
	
	public void setDefaultText (String text) {
		this.setText("");
		this.setToolTipText(text);
	}
}
