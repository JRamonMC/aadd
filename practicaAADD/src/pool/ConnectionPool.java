package pool;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import javax.sql.*;

public class ConnectionPool implements DataSource {
	
	// Valores por defecto
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String DBURL = "jdbc:mysql://localhost:3306/";
	private final static String USER = "root";
	private final static String PASSWORD = "";
	private final static int CONEXIONES_INICIALES = 5;
	private final static int TAM_EXPANSION = 5;
	
	// Atributos de cada instancia
	private String driver;
	private String dbURL;
	private String user;
	private String password;
	
	private HashMap connections;
	
	// Variables de clase
	
	// 1. Cuando el pool sin parametros
	private static DataSource pool = null;
	
	// 2. Cuando utilizamos varios pooles. Se indexa por (cadena-usuario-clave)-(pool)
	private static HashMap pooles = new HashMap();
	
	// Metodo del Singleton sin argumentos. Patron Singleton
	public static DataSource getInstance() throws SQLException, ClassNotFoundException {
		
		// Si no ha creado el pool lo instancia con los valores por defecto
		if (pool == null) {
			ConnectionPool p = new ConnectionPool();
			pool = p;
			
			// Valores por defecto
			p.driver = DRIVER; p.dbURL = DBURL; p.user = USER; p.password = PASSWORD;
			
			// Carga el driver
			Class.forName(DRIVER);
			
			// Crea las conexiones iniciales del pool
			p.connections = new HashMap();
		
			for (int i = 0; i < CONEXIONES_INICIALES; i++) {
				ConnectionWrapper cw = new ConnectionWrapper((ConnectionPool)p, DriverManager.getConnection(p.dbURL, p.user, p.password));
			    p.connections.put(cw, Boolean.FALSE);
			}
		}
		return pool;
	}
	
	// Metodo para acceder a un pool especefico para una conexion y usuario
	public static DataSource getInstance(String dbURL, String usuario, String clave)
		throws SQLException, ClassNotFoundException
	{
		
		// Clave en la tabla hash
		String claveHash = "URL" + dbURL + "USUARIO" + usuario + "CLAVE" + clave;
		
		// Comprueba si existe
		DataSource ds = (DataSource)pooles.get(claveHash);
		if (ds == null) {
			ConnectionPool p = new ConnectionPool();
			ds = p;
			
			// Se asume el mismo tipo de driver
			p.driver = DRIVER; p.dbURL = dbURL; p.user = usuario; p.password = clave;
			
			Class.forName(p.driver);
				
			p.connections = new HashMap();
		
			for (int i = 0; i < CONEXIONES_INICIALES; i++) {
				ConnectionWrapper cw = new ConnectionWrapper((ConnectionPool)p, DriverManager.getConnection(p.dbURL, p.user, p.password));
			    p.connections.put(cw, Boolean.FALSE);
			}
			pooles.put(claveHash, p);
		}
		return ds;
	}
	
	
  // Metodo para recuperar una conexion del DataSource
  public Connection getConnection() throws SQLException {
    Connection con = null;

    Iterator cons = connections.keySet().iterator();
	  
	  // Busca la primera conexion libre
	  // Sincroniza el acceso a la coleccion de conexiones. Importante.
    synchronized (connections) {
      while(cons.hasNext()) {
        con = (Connection)cons.next();
        Boolean b = (Boolean)connections.get(con);
        if (b == Boolean.FALSE) {
			try {
				// Supone un modo de trabajo por defecto Autocommit
            	con.setAutoCommit(true);
          	}
		  	catch(SQLException e) {
				con = new ConnectionWrapper((ConnectionPool)this, DriverManager.getConnection(this.dbURL, this.user, this.password));
            
          	}
          connections.put(con, Boolean.TRUE);
          return con;
        }
      }
    }
	  
	  // Si no quedan conexiones, expande el pool irreversiblemente
	  for(int i = 0; i < TAM_EXPANSION; i++) {
		  
      ConnectionWrapper cw = new ConnectionWrapper((ConnectionPool)this, DriverManager.getConnection(this.dbURL, this.user, this.password));
	  connections.put(cw, Boolean.FALSE);
    }
 
    return getConnection();
  }
	
	// Devuelve una coleccion al pool
	public void returnConnection(Connection returned) {
		
    Connection con;
		Iterator cons = connections.keySet().iterator();
		
		while (cons.hasNext()) {
		
			con = (Connection)cons.next();
			
			if (con == returned) {
			
        		connections.put(con, Boolean.FALSE);
        		break;
      		}
    }
  }

	
// Resto de operaciones de la interface DataSource. No se implementan
	public Connection getConnection(String username, String password)
		throws SQLException
	{
		throw new UnsupportedOperationException();
		
	}
      
	public java.io.PrintWriter getLogWriter() throws SQLException
	{
		throw new UnsupportedOperationException();
		
	}
  
	public void setLogWriter(java.io.PrintWriter out) throws SQLException
	{
		throw new UnsupportedOperationException();
		
	}

  
	public void setLoginTimeout(int seconds) throws SQLException
	{
		throw new UnsupportedOperationException();
		
	}
     
  
	public int getLoginTimeout() throws SQLException
	{
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
