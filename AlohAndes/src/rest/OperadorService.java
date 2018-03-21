package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.Operador;



@Path("operadores")
public class OperadorService {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS REST
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo GET que trae a todos los operadores en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutoriaAlohAndes/rest/operadores <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los operadores que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperadores() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Operador> operadores;
			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			operadores = tm.getAllOperadores();
			return Response.status(200).entity(operadores).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al operador en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialAlohAndes/rest/operadores/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Operador que contiene al operador cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getOperadorById( @PathParam( "id" ) Long id )
	{
		try{
			AlohAndesTM tm = new AlohAndesTM( getPath( ) );
			
			Operador operador = tm.getOperadorById( id );
			return Response.status( 200 ).entity( operador ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	/**
	 * Metodo que recibe un operador en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al operador. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialAlohAndes/rest/operadores <br/>
	 * @param operador JSON con la informacion del operador que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al operador que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOperador(Operador operador) {
		
		//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de AlohAndes 
		return null;
	}
	
	
	

	/**
	 * Metodo que recibe un operador en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al operador.<br/>
	 * @param operador JSON con la informacion del operador que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al operador que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 5A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 5B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

	
	public Response updateOperador(Operador operador) {
		//TODO Requerimiento 5B: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de AlohAndes 
		return null;
	}

	/**
	 * Metodo que recibe un operador en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al operador con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialAlohAndes/rest/operadores <br/>
	 * @param operador JSON con la informacion del operador que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al operador que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

	
	public Response deleteOperador(Operador operador) {
		//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de AlohAndes 
		return null;
	}
}
