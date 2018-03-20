package vos;


import org.codehaus.jackson.annotate.JsonProperty;


public class Servicio {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del Servicio
	 */
	@JsonProperty(value="idservicio")
	private Long idservicio;

	/**
	 * Nombre/descripcion del servicio
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;

	/**
	 * Costo de adquirir el servicio. Numero positivo
	 */
	@JsonProperty(value = "costoextra")
	private Double costoextra;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Servicio
	 * <b>post: </b> Crea el servicio con los valores que entran por parametro
	 * @param id - Id del servicio.
	 * @param descripcion - Descripcion/nombre del servicio
	 * @param costo - El costo de adquirir el servicio
	 */
	public Servicio(@JsonProperty(value="idservicio")Long id,@JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="costoextra")Double costo) {
		this.idservicio = id;
		this.costoextra = costo;
		this.descripcion = descripcion;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	public Long getId() {
		return idservicio;
	}

	public void setId(Long id) {
		this.idservicio = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getCostoextra() {
		return costoextra;
	}

	public void setCostoextra(Double costoextra) {
		this.costoextra = costoextra;
	}
}
