package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.DAOAlojamiento;
import dao.DAOCliente;
import dao.DAOContrato;
import dao.DAOOperador;
import dao.DAOPromocion;
import dao.DAOSeguro;
import dao.DAOServicio;
import vos.Alojamiento;
import vos.Cliente;
import vos.Contrato;
import vos.Operador;
import vos.Promocion;
import vos.Seguro;
import vos.Servicio;


public class AlohAndesTM {
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
	public AlohAndesTM(String contextPathP) {

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

		FileInputStream fileInputStream = new FileInputStream(new File(AlohAndesTM.CONNECTION_DATA_PATH));
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
		System.out.println("[ALOHANDES APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------


	// OPERADOR


	public List<Operador> getAllOperadores() throws Exception {
		DAOOperador daoop = new DAOOperador();
		List<Operador> operadores;
		try 
		{
			this.conn = darConexion();
			daoop.setConn(conn);
			operadores = daoop.getOperadores();
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

	public Operador createOperador(Operador operador)throws SQLException, Exception
	{
		DAOOperador dao = new DAOOperador();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createOperador(operador);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return operador;
		}
	

	public Operador updateOperador(Operador operador)throws SQLException, Exception
	{
		DAOOperador dao = new DAOOperador();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateOperador(operador);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return operador;
		}
	
	public void deleteOperador(int idOperador)throws SQLException, Exception
	{
		DAOOperador dao = new DAOOperador();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteOperador(idOperador);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}
	
	

	//CLIENTE
	public List<Cliente> getAllClientes() throws Exception {
		DAOCliente dacli = new DAOCliente();
		List<Cliente> clientes;
		try 
		{
			this.conn = darConexion();
			dacli.setConn(conn);
			clientes = dacli.getClientes();
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
				dacli.cerrarRecursos();
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
		return clientes;
	}

	public Cliente getClienteById(Long id) throws Exception{
		DAOCliente daocli = new DAOCliente();
		Cliente cliente = null;
		try 
		{
			this.conn = darConexion();
			daocli.setConn(conn);
			cliente = daocli.findClienteById(id);
			if(cliente == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daocli.cerrarRecursos();
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
		return cliente;
	}

	public Cliente createCliente(Cliente cliente)throws SQLException, Exception
	{
		DAOCliente dao = new DAOCliente();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createCliente(cliente);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return cliente;
		}
	

	public Cliente updateCliente(Cliente cliente)throws SQLException, Exception
	{
		DAOCliente dao = new DAOCliente();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateCliente(cliente);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return cliente;
		}
	
	
	public void deleteCliente(long idCliente)throws SQLException, Exception
	{
		DAOCliente dao = new DAOCliente();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteCliente(idCliente);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}
	


	//ALOJAMIENTO

	public List<Alojamiento> getAllAlojamientos() throws Exception {
		DAOAlojamiento daoaloj = new DAOAlojamiento();
		List<Alojamiento> alojamientos;
		try 
		{
			this.conn = darConexion();
			daoaloj.setConn(conn);
			alojamientos = daoaloj.getAlojamientos();
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
				daoaloj.cerrarRecursos();
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
		return alojamientos;
	}

	public Alojamiento getAlojamientoById(Long id) throws Exception{
		DAOAlojamiento daoaloj = new DAOAlojamiento();
		Alojamiento alojamiento = null;
		try 
		{
			this.conn = darConexion();
			daoaloj.setConn(conn);
			alojamiento = daoaloj.findAlojamientoById(id);
			if(alojamiento == null)
			{
				throw new Exception("El alojamiento con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daoaloj.cerrarRecursos();
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
		return alojamiento;
	}

	public Alojamiento createAlojamiento(Alojamiento alojamiento)throws SQLException, Exception
	{
		DAOAlojamiento dao = new DAOAlojamiento();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createAlojamiento(alojamiento);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return alojamiento;
		}
	
	public Alojamiento updateAlojamiento(Alojamiento alojamiento)throws SQLException, Exception
	{
		DAOAlojamiento dao = new DAOAlojamiento();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateAlojamiento(alojamiento);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return alojamiento;
		}
	
	
	public void deleteAlojamiento(long idAlojamiento)throws SQLException, Exception
	{
		DAOAlojamiento dao = new DAOAlojamiento();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteAlojamiento(idAlojamiento);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

	//CONTRATO

	public List<Contrato> getAllContratos() throws Exception {
		DAOContrato daocon = new DAOContrato();
		List<Contrato> contratos;
		try 
		{
			this.conn = darConexion();
			daocon.setConn(conn);
			contratos = daocon.getContratos();
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
				daocon.cerrarRecursos();
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
		return contratos;
	}

	public Contrato getContratoById(Long id) throws Exception{
		DAOContrato daocon = new DAOContrato();
		Contrato contrato = null;
		try 
		{
			this.conn = darConexion();
			daocon.setConn(conn);
			contrato = daocon.findContratoById(id);
			if(contrato == null)
			{
				throw new Exception("El contrato con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daocon.cerrarRecursos();
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
		return contrato;
	}

	public Contrato createContrato(Contrato contrato)throws SQLException, Exception
	{
		DAOContrato dao = new DAOContrato();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createContrato(contrato);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return contrato;
		}
	
	
	public Contrato updateContrato(Contrato contrato)throws SQLException, Exception
	{
		DAOContrato dao = new DAOContrato();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateContrato(contrato);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return contrato;
		}
	
	

	public void deleteContrato(long idContrato)throws SQLException, Exception
	{
		DAOContrato dao = new DAOContrato();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteContrato(idContrato);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

	//PROMOCION
	public List<Promocion> getAllPromociones() throws Exception {
		DAOPromocion daopro = new DAOPromocion();
		List<Promocion> promociones;
		try 
		{
			this.conn = darConexion();
			daopro.setConn(conn);
			promociones = daopro.getPromociones();
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
				daopro.cerrarRecursos();
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
		return promociones;
	}


	public Promocion getPromocionById(Long id) throws Exception{
		DAOPromocion daopro = new DAOPromocion();
		Promocion promocion = null;
		try 
		{
			this.conn = darConexion();
			daopro.setConn(conn);
			promocion = daopro.findPromocionById(id);
			if(promocion == null)
			{
				throw new Exception("La promoción con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daopro.cerrarRecursos();
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
		return promocion;
	}

	public Promocion createPromocion(Promocion promocion)throws SQLException, Exception
	{
		DAOPromocion dao = new DAOPromocion();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createPromocion(promocion);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return promocion;
		}
	

	public Promocion updatePromocion(Promocion promocion)throws SQLException, Exception
	{
		DAOPromocion dao = new DAOPromocion();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updatePromocion(promocion);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return promocion;
		}
	

	public void deletePromocion(long idPromocion)throws SQLException, Exception
	{
		DAOPromocion dao = new DAOPromocion();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deletePromocion(idPromocion);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}


	//SEGURO

	public List<Seguro> getAllSeguros() throws Exception {
		DAOSeguro daoseg = new DAOSeguro();
		List<Seguro> seguros;
		try 
		{
			this.conn = darConexion();
			daoseg.setConn(conn);
			seguros = daoseg.getSeguros();
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
				daoseg.cerrarRecursos();
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
		return seguros;
	}

	public Seguro getSeguroById(Long id) throws Exception{
		DAOSeguro daoseg = new DAOSeguro();
		Seguro seguro = null;
		try 
		{
			this.conn = darConexion();
			daoseg.setConn(conn);
			seguro = daoseg.findSeguroById(id);
			if(seguro == null)
			{
				throw new Exception("El seguro con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daoseg.cerrarRecursos();
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
		return seguro;
	}

	public Seguro createSeguro(Seguro seguro)throws SQLException, Exception
	{
		DAOSeguro dao = new DAOSeguro();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createSeguro(seguro);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return seguro;
		}
	

	public Seguro updateSeguro(Seguro seguro)throws SQLException, Exception
	{
		DAOSeguro dao = new DAOSeguro();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateSeguro(seguro);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return seguro;
		}
	

	public void deleteSeguro(long idSeguro)throws SQLException, Exception
	{
		DAOSeguro dao = new DAOSeguro();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteSeguro(idSeguro);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}

	//SERVICIO

	public List<Servicio> getAllServicios() throws Exception {
		DAOServicio daoser = new DAOServicio();
		List<Servicio> servicios;
		try 
		{
			this.conn = darConexion();
			daoser.setConn(conn);
			servicios = daoser.getServicios();
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
				daoser.cerrarRecursos();
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
		return servicios;
	}

	public Servicio getServicioById(Long id) throws Exception{
		DAOServicio daoser = new DAOServicio();
		Servicio servicio = null;
		try 
		{
			this.conn = darConexion();
			daoser.setConn(conn);
			servicio = daoser.findServicioById(id);
			if(servicio == null)
			{
				throw new Exception("El servicio con el id = " + id + " no se encuentra persistido en la base de datos.");				
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
				daoser.cerrarRecursos();
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
		return servicio;
	}

	public Servicio createServicio(Servicio servicio)throws SQLException, Exception
	{
		DAOServicio dao = new DAOServicio();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.createServicio(servicio);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return servicio;
		}
	
	public Servicio updateServicio(Servicio servicio)throws SQLException, Exception
	{
		DAOServicio dao = new DAOServicio();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.updateServicio(servicio);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return servicio;
		}
	
	public void deleteServicio(long idServicio)throws SQLException, Exception
	{
		DAOServicio dao = new DAOServicio();
			try {
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);	

				dao.deleteServicio(idServicio);

			}catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}finally {
				try {
					conn.setAutoCommit(true);
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			
		}


	//CONTRATOSCLIENTES

	//SERVICIOSCONTRATADOS

	//SERVICIOSOFRECIDOS

	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF1
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Registrar los operadores de alojamiento para AlohAndes
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF2
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Registrar propuestas de alojamientos para AlohAndes
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF3
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Registrar personas habilitadas para utilizar los servicios
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF4
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Registrar una reserva
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF5
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Cancelar una reserva
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RF6
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Retirar una oferta de alojamiento
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC1
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * MOSTRAR EL DINERO RECIBIDO POR CADA PROVEEDOR DE ALOJAMIENTO DURANTE EL AÑO ACTUAL Y EL
AÑO CORRIDO
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC2
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * MOSTRAR LAS 20 OFERTAS MÁS POPULARES
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC3
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC4
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO
DE REQUERIMIENTOS DE DOTACIÓN O SERVICIOS. POR EJEMPLO, COCINETA, TV CABLE, INTERNET, SALA
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC5
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * MOSTRAR EL USO DE ALOHANDES PARA CADA TIPO DE USUARIO DE LA COMUNIDAD
	 */

	//----------------------------------------------------------------------------------------------------------------------------------
	// RFC6
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * MOSTRAR EL USO DE ALOHANDES PARA UN USUARIO DADO (NÚMERO DE NOCHES O MESES CONTRATADOS,
CARACTERÍSTICAS DEL ALOJAMIENTO UTILIZADO, DINERO PAGADO.
	 */

}
