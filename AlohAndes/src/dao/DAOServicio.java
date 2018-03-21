package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Servicio;


public class DAOServicio {
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
	 * Metodo constructor de la clase DAOServicio <br/>
	 */
	public DAOServicio() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los servicios en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>
	 * @return	lista con la informacion de todos los servicios que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Servicio> getServicios() throws SQLException, Exception {
		ArrayList<Servicio> servicios = new ArrayList<Servicio>();

		String sql = String.format("SELECT * FROM %1$s.SERVICIOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			servicios.add(convertResultSetToServicio(rs));
		}
		return servicios;
	}

	

	/**
	 * Metodo que obtiene la informacion del servicio en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/> 
	 * @param id el identificador del bebedor
	 * @return la informacion del bebedor que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Servicio findServicioById(Long id) throws SQLException, Exception 
	{
		Servicio servicio = null;

		String sql = String.format("SELECT * FROM %1$s.SERVICIOS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			servicio = convertResultSetToServicio(rs);
		}

		return servicio;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo servicio en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param servicio Servicio que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addServicio(Servicio servicio) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.SERVICIOS ( DESCRIPCION, COSTOEXTRA) VALUES (%2$s, '%3$s')", 
				USUARIO, 
				servicio.getDescripcion(),
				servicio.getCostoextra());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza la informacion del servicio en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param servicio Servicio que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateServicio(Servicio servicio) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.SERVICIOS ", USUARIO));
		sql.append (String.format (
				"SET DESCRIPCION = '%1$s', COSTOEXTRA = '%2$s'",
				servicio.getDescripcion(), servicio.getCostoextra()));
		sql.append ("WHERE IDSERVICIO = " + servicio.getId());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del servicio en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion Ha sido inicializada <br/>  
	 * @param servicio Servicio que desea eliminar en la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteServicio(Servicio servicio) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.SERVICIOS WHERE ID = %2$d", USUARIO, servicio.getId());

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SERVICIOS) en una instancia de la clase Servicio.
	 * @param resultSet ResultSet con la informacion de un servicio que se obtuvo de la base de datos.
	 * @return Servicio cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SERVICIOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Servicio convertResultSetToServicio(ResultSet resultSet) throws SQLException {

		Long id = Long.parseLong(resultSet.getString("IDSERVICIO"));
		String descripcion = resultSet.getString("DESCRIPCION");
		Double costoextra = Double.parseDouble(resultSet.getString("COSTOEXTRA"));

		Servicio sv = new Servicio(id, descripcion, costoextra);

		return sv;
	}

}

