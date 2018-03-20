package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Alojamiento {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del alojamiento
	 */
	@JsonProperty(value="idalojamiento")
	private Long idalojamiento;

	/**
	 * Id del operador
	 */
	@JsonProperty(value="idoperador")
	private Long idoperador;

	/**
	 * Tipo del alojamiento
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Booleano que indica si está amoblado
	 */
	@JsonProperty(value="amoblado")
	private Boolean amoblado;

	/**
	 * La ubicacion de la vivienda
	 */
	@JsonProperty(value = "ubicacion")
	private String ubicacion;
	
	/**
	 * El costo diario de la vivienda
	 */
	@JsonProperty(value = "costodia")
	private Double costodia;
	
	/**
	 * La capacidad de alojamiento en numero de personas
	 */
	@JsonProperty(value = "capacidad")
	private Integer capacidad;
	
	/**
	 * El tamano de la vivienda en m2
	 */
	@JsonProperty(value = "tamanio")
	private Double tamanio;
	
	/**
	 * Booleano que indica si la oferta es para contratar la vivienda compartida
	 */
	@JsonProperty(value = "compartida")
	private Boolean compartida;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Alojamiento
	 * <b>post: </b> Crea el alojamiento con los valores que entran por parametro
	 * @param id - Id del alojamiento.
	 * @param idoperador - Identificador del operador que ofrece el alojamiento
	 * @param tipo - El tipo de alojamiento que se ofrece
	 * @param amoblado - Booleano que indica si se ofrece amoblado
	 * @param ubicacion - La ubicacion en la ciudad de la vivienda
	 * @param costodia - El costo de un dia de estadia en el alojamiento
	 * @param capacidad - El numero de personas que puede haber en el alojamiento
	 * @param tamanio - El tamanio de la vivienda en m2
	 * @param compartida - Booleano que indica si se ofrece compartida 
	 */
	public Alojamiento(@JsonProperty(value="idalojamiento")Long idalojamiento,@JsonProperty(value="idoperador")Long idoperador,@JsonProperty(value="tipo")String tipo , @JsonProperty(value="amoblado") Boolean amoblado, @JsonProperty(value="ubicacion") String ubicacion, @JsonProperty(value= "costodia") Double costodia, @JsonProperty(value="capacidad") Integer capacidad, @JsonProperty(value="tamanio") Double tamanio, @JsonProperty(value="compartida")Boolean compartida) {
		this.amoblado = amoblado;
		this.capacidad = capacidad;
		this.compartida = compartida;
		this.costodia = costodia;
		this.idalojamiento = idalojamiento;
		this.idoperador = idoperador;
		this.tamanio = tamanio;
		this.tipo = tipo;
		this.ubicacion =ubicacion;
	}
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	public Long getIdalojamiento() {
		return idalojamiento;
	}

	public void setIdalojamiento(Long idalojamiento) {
		this.idalojamiento = idalojamiento;
	}

	public Long getIdoperador() {
		return idoperador;
	}

	public void setIdoperador(Long idoperador) {
		this.idoperador = idoperador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getAmoblado() {
		return amoblado;
	}

	public void setAmoblado(Boolean amoblado) {
		this.amoblado = amoblado;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Double getCostodia() {
		return costodia;
	}

	public void setCostodia(Double costodia) {
		this.costodia = costodia;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Double getTamanio() {
		return tamanio;
	}

	public void setTamanio(Double tamanio) {
		this.tamanio = tamanio;
	}

	public Boolean getCompartida() {
		return compartida;
	}

	public void setCompartida(Boolean compartida) {
		this.compartida = compartida;
	}

}
