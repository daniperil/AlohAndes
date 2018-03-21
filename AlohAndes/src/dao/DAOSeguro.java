package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Seguro;

public class DAOSeguro {
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
	 * Metodo constructor de la clase DAOSeguro<br/>
	 */
	public DAOSeguro() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los seguros en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>
	 * @return	lista con la informacion de todos los seguros que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Seguro> getSeguros() throws SQLException, Exception {
		ArrayList<Seguro> seguros = new ArrayList<Seguro>();

		String sql = String.format("SELECT * FROM %1$s.OPERADORES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			seguros.add(convertResultSetToSeguro(rs));
		}
		return seguros;
	}



	/**
	 * Metodo que obtiene la informacion del seguro en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/> 
	 * @param id el identificador del seguro
	 * @return la informacion del seguro que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el seguro conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Seguro findSeguroById(Long id) throws SQLException, Exception 
	{
		Seguro seguro = null;

		String sql = String.format("SELECT * FROM %1$s.OPERADORES WHERE IDOPERADOR = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			seguro = convertResultSetToSeguro(rs);
		}

		return seguro;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo seguro en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param servicio Seguro que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void createSeguro(Seguro seguro) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.OPERADORES (NOMBRE, TIPOOPERADOR, REGISTROCC, REGISTROSI, VINCULO) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s')", 
				USUARIO  
				);
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza la informacion del seguro en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param seguro Seguro que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateSeguro(Seguro seguro) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.OPERADORES ", USUARIO));
		sql.append (String.format (
				"SET NOMBRE= '%1$s', TIPOOPERADOR= '%2$s', REGISTROCC = '%3$s', REGISTROSI = '%4$s', VINCULO= '%5$s'"
				));
		sql.append ("WHERE IDOPERADOR = " + seguro.getIdseguro());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la informacion del seguro en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion Ha sido inicializada <br/>  
	 * @param seguro Seguro que desea eliminar en la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteSeguro(long idSeguro) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.OPERADORES WHERE IDOPERADOR= %2$d", USUARIO, idSeguro);

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SERVICIOS) en una instancia de la clase Seguro.
	 * @param resultSet ResultSet con la informacion de un servicio que se obtuvo de la base de datos.
	 * @return Servicio cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SERVICIOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Seguro convertResultSetToSeguro(ResultSet resultSet) throws SQLException {

		Long idseguro = Long.parseLong(resultSet.getString("IDCONTRATO"));
		String nombre = resultSet.getString("NOMBRE");
		String vinculo = resultSet.getString("VINCULO");
		String tiposeguro = resultSet.getString("TIPOOPERADOR");
		Boolean registrocc = Boolean.parseBoolean(resultSet.getString("REGISTROCC"));
		Boolean registrosi = Boolean.parseBoolean(resultSet.getString("REGISTROSI"));

		
		Seguro sv = null;//new Seguro(idseguro, nombre, tiposeguro, registrocc, registrosi, vinculo);

		return sv;
	}
}
