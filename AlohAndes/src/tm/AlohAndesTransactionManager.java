package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.DAOOperador;
import vos.Operador;


public class AlohAndesTransactionManager {
	//----------------------------------------------------------------------------------------------------------------------------------
		// CONSTANTES
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
		 */
		private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

		/**
		 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
		 */
		private static String CONNECTION_DATA_PATH;

		//----------------------------------------------------------------------------------------------------------------------------------
		// ATRIBUTOS
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
		 */
		private String user;

		/**
		 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
		 */
		private String password;

		/**
		 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
		 */
		private String url;

		/**
		 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
		 */
		private String driver;
		
		/**
		 * Atributo que representa la conexion a la base de datos
		 */
		private Connection conn;

		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE CONEXION E INICIALIZACION
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * <b>Metodo Contructor de la Clase AlohAndesTransactionManager</b> <br/>
		 * <b>Postcondicion: </b>	Se crea un objeto  AlohAndesTransactionManager,
		 * 						 	Se inicializa el path absoluto del archivo de conexion,
		 * 							Se inicializna los atributos para la conexion con la Base de Datos
		 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
		 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
		 * @throws ClassNotFoundException 
		 */
		public AlohAndesTransactionManager(String contextPathP) {
			
			try {
				CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
				initializeConnectionData();
			} 
			catch (ClassNotFoundException e) {			
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
		 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
		 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
		 * @throws ClassNotFoundException 
		 */
		private void initializeConnectionData() throws IOException, ClassNotFoundException {

			FileInputStream fileInputStream = new FileInputStream(new File(AlohAndesTransactionManager.CONNECTION_DATA_PATH));
			Properties properties = new Properties();
			
			properties.load(fileInputStream);
			fileInputStream.close();
			
			this.url = properties.getProperty("url");
			this.user = properties.getProperty("usuario");
			this.password = properties.getProperty("clave");
			this.driver = properties.getProperty("driver");
			
			//Class.forName(driver);
		}

		/**
		 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
		 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
		 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
		 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
		 */
		private Connection darConexion() throws SQLException {
			System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
			return DriverManager.getConnection(url, user, password);
		}

		
		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS TRANSACCIONALES
		//----------------------------------------------------------------------------------------------------------------------------------
		
		
		
		public List<Operador> getAllOperadores() throws Exception {
			DAOOperador daoop = new DAOOperador();
			List<Operador> operadores;
			try 
			{
				this.conn = darConexion();
				daoop.setConn(conn);
				
				//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
				operadores = daoop.getOperadors();
			}
			catch (SQLException sqlException) {
				System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
				sqlException.printStackTrace();
				throw sqlException;
			} 
			catch (Exception exception) {
				System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			} 
			finally {
				try {
					daoop.cerrarRecursos();
					if(this.conn!=null){
						this.conn.close();					
					}
				}
				catch (SQLException exception) {
					System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return operadores;
		}

		public Operador getOperadorById(Long id) throws Exception{
			DAOOperador daoop = new DAOOperador();
			Operador operador = null;
			try 
			{
				this.conn = darConexion();
				daoop.setConn(conn);
				operador = daoop.findOperadorById(id);
				if(operador == null)
				{
					throw new Exception("El operador con el id = " + id + " no se encuentra persistido en la base de datos.");				
				}
			} 
			catch (SQLException sqlException) {
				System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
				sqlException.printStackTrace();
				throw sqlException;
			} 
			catch (Exception exception) {
				System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			} 
			finally {
				try {
					daoop.cerrarRecursos();
					if(this.conn!=null){
						this.conn.close();					
					}
				}
				catch (SQLException exception) {
					System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return operador;
		}
		
		public Operador updateOperador() {
			return null;
		}
		public void deleteOperador() {}

}
