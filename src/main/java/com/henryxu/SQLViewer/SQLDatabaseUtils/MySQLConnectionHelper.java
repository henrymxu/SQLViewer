package src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.Interfaces.ConnectionHelper;

public class MySQLConnectionHelper implements ConnectionHelper {
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	private static final String URL = "jdbc:mysql://localhost:3306/f1db";
    private static final String USER = "java";
    private static final String PASSWORD = "admin";
    
    private Connection sqlConnection;
    
    public MySQLConnectionHelper () {
		sqlConnection = getServerConnection();
    }
    
    /* (non-Javadoc)
	 * @see src.main.java.com.henryxu.SQLViewer.SQLDatabaseUtils.ConnectionHelper#getConnection()
	 */
    @Override
	public Connection getConnection() {
    	return sqlConnection;
    }
    
    private Connection getServerConnection()
    {
        try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
}
