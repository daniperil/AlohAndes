package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Seguro {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Id del Seguro
	 */
	@JsonProperty(value="idseguro")
	private Long idseguro;
	
	/**
	 * Nombre de la aseguradora
	 */
	@JsonProperty(value="aseguradora")
	private String aseguradora;
	
	/**
	 * Costo del seguro 
	 */
	@JsonProperty(value="costo")
	private Double costo;
	
	/**
	 * Id del alojamiento 
	 */
	
	@JsonProperty(value="idalojamiento")
	private Long idalojamiento;
	
	/**
	 * Id del contrato 
	 */
	@JsonProperty(value="idcontrato")
	private Long idcontrato;
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Seguro
	 * <b>post: </b> Crea el segurocon los valores que entran por parametro
	 * @param id - Id del seguro.
	 * @param aseguradora - Nombre de la aseguradora
	 * @param costo - El costo del seguro
	 * @param idalojamiento - El identificador del alojamiento que hace la promoción
	 * @param idcontrato - El identificador del contrato que lo obtiene
	 */
	public Seguro(@JsonProperty(value="idseguro")Long id,@JsonProperty(value="aseguradora")String aseguradora,@JsonProperty(value="costo")Double costo, @JsonProperty(value="idalojamiento") Long idalojamiento, @JsonProperty(value="idcontrato") Long idcontrato) {
		this.idseguro= id;
		this.costo = costo;
		this.aseguradora = aseguradora;
		this.idalojamiento = idalojamiento;
		this.idcontrato = idcontrato;
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	
	public Long getIdseguro() {
		return idseguro;
	}


	public void setIdseguro(Long idseguro) {
		this.idseguro = idseguro;
	}


	public String getAseguradora() {
		return aseguradora;
	}


	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}


	public Double getCosto() {
		return costo;
	}


	public void setCosto(Double costo) {
		this.costo = costo;
	}


	public Long getIdalojamiento() {
		return idalojamiento;
	}


	public void setIdalojamiento(Long idalojamiento) {
		this.idalojamiento = idalojamiento;
	}


	public Long getIdcontrato() {
		return idcontrato;
	}


	public void setIdcontrato(Long idcontrato) {
		this.idcontrato = idcontrato;
	}

}
