package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Operador {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Id del operador
	 */
	@JsonProperty(value="idoperador")
	private Long idoperador;
	
	/**
	 * Nombre del operador
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Tipo del operador 
	 */
	@JsonProperty(value="tipooperador")
	private String tipooperador;
	
	/**
	 * Registro en la camara de comercio 
	 */
	@JsonProperty(value="registrocc")
	private Boolean registrocc;
	
	/**
	 * Registro en la superintendencia financiera
	 */
	@JsonProperty(value="registrosi")
	private Boolean registrosi;
	
	/**
	 * Vinculo con la universidad
	 */
	@JsonProperty(value = "vinculo")
	private String vinculo;
	
	/**
	 * Los alojamientos ofertados por este operador
	 */
	@JsonProperty(value = "idsAlojamientos")
	private ArrayList<Long> idsAlojamientos; 
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Operador
	 * <b>post: </b> Crea el operador con los valores que entran por parametro
	 * @param id - Id del operador.
	 * @param nombre - Nombre del operador
	 * @param tipooperador - El tipo del operador
	 * @param registrocc - El registro en la camara de comercio
	 * @param registrosi - El registro en la superintendencia financiera
	 * @param vinculo - El vinculo con la universidad
	 */
	public Operador(@JsonProperty(value="idoperador")Long id,@JsonProperty(value="nombre")String nombre,@JsonProperty(value="tipooperador")String tipooperador, @JsonProperty(value="registrocc") boolean registrocc, @JsonProperty(value="registrosi") boolean registrosi, @JsonProperty(value= "vinculo") String vinculo) {
		this.idoperador= id;
		this.nombre = nombre;
		this.tipooperador = tipooperador;
		this.registrocc = registrocc;
		this.registrosi = registrosi;
		this.vinculo = vinculo;
		this.idsAlojamientos = new ArrayList<Long>();
	}
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------


	public Long getIdoperador() {
		return idoperador;
	}

	public void setIdoperador(Long idoperador) {
		this.idoperador = idoperador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipooperador() {
		return tipooperador;
	}

	public void setTipooperador(String tipooperador) {
		this.tipooperador = tipooperador;
	}

	public Boolean getRegistrocc() {
		return registrocc;
	}

	public void setRegistrocc(Boolean registrocc) {
		this.registrocc = registrocc;
	}

	public Boolean getRegistrosi() {
		return registrosi;
	}

	public void setRegistrosi(Boolean registrosi) {
		this.registrosi = registrosi;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}


	public ArrayList<Long> getIdsAlojamientos() {
		return idsAlojamientos;
	}


	public void setIdsAlojamientos(ArrayList<Long> idsAlojamientos) {
		this.idsAlojamientos = idsAlojamientos;
	}
}
