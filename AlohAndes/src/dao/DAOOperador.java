package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;

public class DAOOperador {
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
	 * Metodo constructor de la clase DAOOperador<br/>
	 */
	public DAOOperador() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los operadors en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>
	 * @return	lista con la informacion de todos los operadors que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Operador> getOperadors() throws SQLException, Exception {
		ArrayList<Operador> operadors = new ArrayList<Operador>();

		String sql = String.format("SELECT * FROM %1$s.OPERADORES", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Operador actual = convertResultSetToOperador(rs);
			ArrayList<Long> alojamientosActual = new ArrayList<Long>();
			
			//Busco las ofertas de cada quien
			String sql2 = String.format("SELECT IDALOJAMIENTO FROM %1$s.ALOJAMIENTOS WHERE IDOPERADOR= %2$s", USUARIO, actual.getIdoperador());

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			
			while(rs2.next()) {
				Long id = Long.parseLong(rs2.getString("IDALOJAMIENTO"));
				alojamientosActual.add(id);
			}
			actual.setIdsAlojamientos(alojamientosActual);
			operadors.add(actual);
			
		}
		return operadors;
	}



	/**
	 * Metodo que obtiene la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/> 
	 * @param id el identificador del operador
	 * @return la informacion del operador que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el operador conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Operador findOperadorById(Long id) throws SQLException, Exception 
	{
		Operador operador = null;

		String sql = String.format("SELECT * FROM %1$s.OPERADORES WHERE IDOPERADOR = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			operador = convertResultSetToOperador(rs);
		}
		
		ArrayList<Long> idsAlojamientos = new ArrayList<Long>();
		
		String sqllist = String.format("SELECT IDALOJAMIENTO FROM %1$s.ALOJAMIENTOS WHERE IDOPERADOR = %2$d", USUARIO, id);
		PreparedStatement prepStmt2 = conn.prepareStatement(sqllist);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt.executeQuery();
		
		while(rs2.next()) {
			idsAlojamientos.add(Long.parseLong(rs2.getString("IDALOJAMIENTO")));
		}
		operador.setIdsAlojamientos(idsAlojamientos);

		return operador;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param servicio Operador que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addOperador(Operador operador) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.OPERADORES (NOMBRE, TIPOOPERADOR, REGISTROCC, REGISTROSI, VINCULO) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s')", 
				USUARIO,  
				operador.getNombre(),
				operador.getTipooperador(),
				operador.getRegistrocc(),
				operador.getRegistrosi(),
				operador.getVinculo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param operador Operador que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateOperador(Operador operador) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.OPERADORES ", USUARIO));
		sql.append (String.format (
				"SET NOMBRE= '%1$s', TIPOOPERADOR= '%2$s', REGISTROCC = '%3$s', REGISTROSI = '%4$s', VINCULO= '%5$s'",
				operador.getNombre(), operador.getTipooperador(),operador.getRegistrocc(), operador.getRegistrosi(),operador.getVinculo()));
		sql.append ("WHERE IDOPERADOR = " + operador.getIdoperador());
		System.out.println(sql);		

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la informacion del operador en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion Ha sido inicializada <br/>  
	 * @param operador Operador que desea eliminar en la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteOperador(Operador operador) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.OPERADORES WHERE IDOPERADOR= %2$d", USUARIO, operador.getIdoperador());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		//Eliminado en cascada de las tablas
//		String sql2 = String.format("DELETE FROM %1$s.ALOJAMIENTOS WHERE IDOPERADOR= %2$d", USUARIO, operador.getIdoperador());
//
//		System.out.println(sql2);
//
//		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
//		recursos.add(prepStmt2);
//		prepStmt2.executeQuery();
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SERVICIOS) en una instancia de la clase Operador.
	 * @param resultSet ResultSet con la informacion de un servicio que se obtuvo de la base de datos.
	 * @return Servicio cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SERVICIOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Operador convertResultSetToOperador(ResultSet resultSet) throws SQLException {

		Long idoperador = Long.parseLong(resultSet.getString("IDCONTRATO"));
		String nombre = resultSet.getString("NOMBRE");
		String vinculo = resultSet.getString("VINCULO");
		String tipooperador = resultSet.getString("TIPOOPERADOR");
		Boolean registrocc = Boolean.parseBoolean(resultSet.getString("REGISTROCC"));
		Boolean registrosi = Boolean.parseBoolean(resultSet.getString("REGISTROSI"));

		
		Operador sv = new Operador(idoperador, nombre, tipooperador, registrocc, registrosi, vinculo);

		return sv;
	}
}
