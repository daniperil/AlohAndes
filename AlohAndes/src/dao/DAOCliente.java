package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;

public class DAOCliente {
	//----------------------------------------------------------------------------------------------------------------------------------
		// CONSTANTES
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Constante para indicar el usuario Oracle
		 */
		public final static String USUARIO = "ISIS2304A461810";


		//----------------------------------------------------------------------------------------------------------------------------------
		// ATRIBUTOS
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Arraylists de recursos que se usan para la ejecucion de sentencias SQL
		 */
		private ArrayList<Object> recursos;

		/**
		 * Atributo que genera la conexion a la base de datos
		 */
		private Connection conn;

		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE INICIALIZACION
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Metodo constructor de la clase DAOCliente<br/>
		 */
		public DAOCliente() {
			recursos = new ArrayList<Object>();
		}

		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS DE COMUNICACION CON LA BASE DE DATOS
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Metodo que obtiene la informacion de todos los clientes en la Base de Datos <br/>
		 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>
		 * @return	lista con la informacion de todos los clientes que se encuentran en la Base de Datos
		 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public ArrayList<Cliente> getClientes() throws SQLException, Exception {
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();

			String sql = String.format("SELECT * FROM %1$s.CLIENTES", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				clientes.add(convertResultSetToCliente(rs));
			}
			
			ArrayList<Long> idsContratos = new ArrayList<Long>();
			return clientes;
		}



		/**
		 * Metodo que obtiene la informacion del cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
		 * <b>Precondicion: </b> la conexion ha sido inicializada <br/> 
		 * @param id el identificador del cliente
		 * @return la informacion del cliente que cumple con los criterios de la sentecia SQL
		 * 			Null si no existe el cliente conlos criterios establecidos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public Cliente findClienteById(Long id) throws SQLException, Exception 
		{
			Cliente cliente = null;

			String sql = String.format("SELECT * FROM %1$s.CLIENTES WHERE IDCLIENTE = %2$d", USUARIO, id); 

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				cliente = convertResultSetToCliente(rs);
			}

			return cliente;
		}

		/**
		 * Metodo que agregar la informacion de un nuevo cliente en la Base de Datos a partir del parametro ingresado<br/>
		 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
		 * @param servicio Cliente que desea agregar a la Base de Datos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public void addCliente(Cliente cliente) throws SQLException, Exception {

			String sql = String.format("INSERT INTO %1$s.CLIENTES (NOMBRE, VINCULO, LOGIN, PASSW) VALUES (%2$s, '%3$s', '%4$s', '%5$s')", 
					USUARIO,  
					cliente.getNombre(),
					cliente.getVinculo(),
					cliente.getLogin(),
					cliente.getpassw());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

		}

		/**
		 * Metodo que actualiza la informacion del cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
		 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
		 * @param cliente Cliente que desea actualizar a la Base de Datos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public void updateCliente(Cliente cliente) throws SQLException, Exception {

			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.CLIENTES ", USUARIO));
			sql.append (String.format (
					"SET NOMBRE = '%1$s', VINCULO= '%2$s', LOGIN= '%3$s', PASSW= '%4$s'",
					 cliente.getNombre(), cliente.getVinculo(), cliente.getLogin(), cliente.getpassw()));
			sql.append ("WHERE IDCLIENTE = " + cliente.getIdcliente());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

		/**
		 * Metodo que elimina la informacion del cliente en la Base de Datos que tiene el identificador dado por parametro<br/>
		 * <b>Precondicion: </b> la conexion Ha sido inicializada <br/>  
		 * @param cliente Cliente que desea eliminar en la Base de Datos
		 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
		 * @throws Exception Si se genera un error dentro del metodo.
		 */
		public void deleteCliente(Cliente cliente) throws SQLException, Exception {

			String sql = String.format("DELETE FROM %1$s.CLIENTES WHERE IDCLIENTE = %2$d", USUARIO, cliente.getIdcliente());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}


		//----------------------------------------------------------------------------------------------------------------------------------
		// METODOS AUXILIARES
		//----------------------------------------------------------------------------------------------------------------------------------

		/**
		 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
		 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
		 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
		 */
		public void setConn(Connection connection){
			this.conn = connection;
		}

		/**
		 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
		 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
		 */
		public void cerrarRecursos() {
			for(Object ob : recursos){
				if(ob instanceof PreparedStatement)
					try {
						((PreparedStatement) ob).close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
			}
		}

		/**
		 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SERVICIOS) en una instancia de la clase Cliente.
		 * @param resultSet ResultSet con la informacion de un servicio que se obtuvo de la base de datos.
		 * @return Servicio cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SERVICIOS.
		 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
		 */
		public Cliente convertResultSetToCliente(ResultSet resultSet) throws SQLException {

			Long id = Long.parseLong(resultSet.getString("IDCLIENTE"));
			String nombre= resultSet.getString("NOMBRE");
			String vinculo = resultSet.getString("VINCULO");
			String login = resultSet.getString("LOGIN");
			String passw= resultSet.getString("PASSW");
			
			Cliente sv = new Cliente(id, nombre, vinculo, login, passw);

			return sv;
		}

}
