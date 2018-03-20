package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Promocion {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id de la promocion
	 */
	@JsonProperty(value="idpromocion")
	private Long idpromocion;

	/**
	 * Precio con descuento
	 */
	@JsonProperty(value="precio")
	private Double precio;

	/**
	 * Numero de dias por el que aplica la oferta 
	 */
	@JsonProperty(value="numdias")
	private Integer numdias;

	/**
	 * Id del alojamiento 
	 */

	@JsonProperty(value="idalojamiento")
	private Long idalojamiento;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Promocion
	 * <b>post: </b> Crea la promocion con los valores que entran por parametro
	 * @param id - Id de la promocion.
	 * @param precio - Precio con descuento
	 * @param numdias - El numero de dias por el cual se da la oferta 
	 * @param idalojamiento - El identificador del alojamiento que ofrece la promocion
	 */
	public Promocion(@JsonProperty(value="idpromocion")Long idpromocion,@JsonProperty(value="numdias") Integer numdias,@JsonProperty(value="precio")Double precio, @JsonProperty(value="idalojamiento") Long idalojamiento) {
		this.idpromocion= idpromocion;
		this.precio = precio;
		this.numdias = numdias;
		this.idalojamiento = idalojamiento;
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	
	public Long getIdpromocion() {
		return idpromocion;
	}

	public void setIdpromocion(Long idpromocion) {
		this.idpromocion = idpromocion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getNumdias() {
		return numdias;
	}

	public void setNumdias(Integer numdias) {
		this.numdias = numdias;
	}

	public Long getIdalojamiento() {
		return idalojamiento;
	}

	public void setIdalojamiento(Long idalojamiento) {
		this.idalojamiento = idalojamiento;
	}
}
