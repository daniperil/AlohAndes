package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Contrato;

public class DAOContrato {
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
	 * Metodo constructor de la clase DAOContrato<br/>
	 */
	public DAOContrato() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los contratos en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>
	 * @return	lista con la informacion de todos los contratos que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Contrato> getContratos() throws SQLException, Exception {
		ArrayList<Contrato> contratos = new ArrayList<Contrato>();

		String sql = String.format("SELECT * FROM %1$s.CONTRATOS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Contrato actual= convertResultSetToContrato(rs);
			ArrayList<Long> idsServActual = new ArrayList<Long>();
			String sql2 = String.format("SELECT IDSERVICIO FROM %1$s.SERVICIOSCONTRATADOS", USUARIO);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
					
			while(rs2.next()) {
				Long idActual = Long.parseLong(rs2.getString("IDSERVICIO"));
				idsServActual.add(idActual);
			}
			actual.setServicios(idsServActual);
			contratos.add(actual);
			
		}
		return contratos;
	}



	/**
	 * Metodo que obtiene la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/> 
	 * @param id el identificador del contrato
	 * @return la informacion del contrato que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el contrato conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Contrato findContratoById(Long id) throws SQLException, Exception 
	{
		Contrato contrato = null;

		String sql = String.format("SELECT * FROM %1$s.CONTRATOS WHERE IDCONTRATO = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			contrato = convertResultSetToContrato(rs);
		}
		
		ArrayList<Long> idsServActual = new ArrayList<Long>();
		String sql2 = String.format("SELECT IDSERVICIO FROM %1$s.SERVICIOSCONTRATADOS WHERE IDCONTRATO = $2$s", USUARIO, id);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
				
		while(rs2.next()) {
			Long idActual = Long.parseLong(rs2.getString("IDSERVICIO"));
			idsServActual.add(idActual);
		}
		contrato.setServicios(idsServActual);

		return contrato;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo contrato en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param servicio Contrato que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void createContrato(Contrato contrato) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.CONTRATOS (FECHAINICIO, FECHAFIN, COSTOTOTAL, IDOPERADOR, IDALOJAMIENTO, ESTADO) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
				USUARIO,  
				contrato.getFechainicio(),
				contrato.getFechafin(),
				contrato.getCostototal(),
				contrato.getIdoperador(),
				contrato.getIdalojamiento(), 
				contrato.getEstado());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion ha sido inicializada <br/>  
	 * @param contrato Contrato que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateContrato(Contrato contrato) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.CONTRATOS ", USUARIO));
		sql.append (String.format (
				"SET FECHAINICIO = '%1$s', FECHAFIN = '%2$s', COSTOTOTAL= '%3$s', IDOPERADOR= '%4$s', IDALOJAMIENTO= '%5$s', ESTADO= '%6$s'",
				contrato.getFechainicio(), contrato.getFechafin(), contrato.getCostototal(), contrato.getIdoperador(), contrato.getIdalojamiento(), contrato.getEstado()));
		sql.append ("WHERE IDCONTRATO = " + contrato.getIdcontrato());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion Ha sido inicializada <br/>  
	 * @param contrato Contrato que desea eliminar en la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteContrato(long idContrato) throws SQLException, Exception {


		String sql = String.format("DELETE FROM %1$s.CONTRATOS WHERE IDCONTRATO = %2$d", USUARIO, idContrato);

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SERVICIOS) en una instancia de la clase Contrato.
	 * @param resultSet ResultSet con la informacion de un servicio que se obtuvo de la base de datos.
	 * @return Servicio cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SERVICIOS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Contrato convertResultSetToContrato(ResultSet resultSet) throws SQLException {

		Long idcontrato = Long.parseLong(resultSet.getString("IDCONTRATO"));
		Date fechainicio = new Date(new java.util.Date(resultSet.getString("FECHAINICIO")).getTime());
		Date fechafin = new Date(new java.util.Date(resultSet.getString("FECHAFIN")).getTime());
		Double costototal = Double.parseDouble(resultSet.getString("COSTOTOTAL"));
		Long idoperador = Long.parseLong(resultSet.getString("IDOPERADOR"));
		Long idalojamiento = Long.parseLong(resultSet.getString("IDALOJAMIENTO"));
		String estado = resultSet.getString("ESTADO");

		Contrato sv = new Contrato(idcontrato, fechainicio, fechafin, costototal, idoperador, idalojamiento, estado);

		return sv;
	}

}
