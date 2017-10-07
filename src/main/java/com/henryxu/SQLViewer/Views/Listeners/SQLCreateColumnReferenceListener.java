package src.main.java.com.henryxu.SQLViewer.Views.Listeners;

public interface SQLCreateColumnReferenceListener {
	public void onReferenceSelected (String referenceTable, String referenceColumn, String idColumn);
	public void onTargetSelected (String targetTable, String targetColumn);
}
