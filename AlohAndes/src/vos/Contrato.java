package vos;

import java.sql.Date;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Contrato {
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del contrato
	 */
	@JsonProperty(value="idcontrato")
	private Long idcontrato;

	/**
	 * Fecha de inicio del contrato
	 */
	@JsonProperty(value="fechainicio")
	private Date fechainicio;

	/**
	 * Fecha de fin del contrato 
	 */
	@JsonProperty(value="fechafin")
	private Date fechafin;

	/**
	 * Valor a pagar por el usuario 
	 */
	@JsonProperty(value="costototal")
	private Double costototal;

	/**
	 * Identificador del operador con el que se firma el contrato
	 */
	@JsonProperty(value="idoperador")
	private Long idoperador;
	
	/**
	 * Identificador del alojamiento que se quiere contratar
	 */
	@JsonProperty(value="idalojamiento")
	private Long idalojamiento;
	
	/**
	 * Estado del contrato
	 */
	@JsonProperty(value = "estado")
	private String estado;

	/**
	 * Los servicios que se especifican en el contrato
	 */
	@JsonProperty(value = "servicios")
	private ArrayList<Long> servicios;
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Contrato
	 * <b>post: </b> Crea el contrato con los valores que entran por parametro
	 * @param id - Id del contrato.
	 * @param fechainicio - Fecha de inicio del contrato
	 * @param fechafin - Fecha de finalización del contrato
	 * @param costoTotal - Valor total a pagar por el contrato
	 * @param idoperador - El identificador del operador que firma el contrato
	 * @param idalojamiento - El identificador del alojamiento que se va a contratar
	 * @param estado - El estado del contrato. 
	 */
	public Contrato(@JsonProperty(value="idcontrato")Long id,@JsonProperty(value="fechainicio")Date fechainicio,@JsonProperty(value="fechafin") Date fechafin, @JsonProperty(value="costototal") Double costototal, @JsonProperty(value="idoperador") Long idoperador, @JsonProperty(value= "idalojamiento") Long idalojamiento, @JsonProperty(value="estado") String estado) {
		this.idcontrato = id;
		this.fechafin = fechafin;
		this.fechainicio = fechainicio;
		this.costototal = costototal;
		this.estado = estado;
		this.idalojamiento = idalojamiento;
		this.idoperador = idoperador;
		servicios = new ArrayList<Long>();
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	public Long getIdcontrato() {
		return idcontrato;
	}

	public void setIdcontrato(Long idcontrato) {
		this.idcontrato = idcontrato;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Double getCostototal() {
		return costototal;
	}

	public void setCostototal(Double costototal) {
		this.costototal = costototal;
	}

	public Long getIdoperador() {
		return idoperador;
	}

	public void setIdoperador(Long idoperador) {
		this.idoperador = idoperador;
	}

	public Long getIdalojamiento() {
		return idalojamiento;
	}

	public void setIdalojamiento(Long idalojamiento) {
		this.idalojamiento = idalojamiento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public ArrayList<Long> getServicios() {
		return servicios;
	}


	public void setServicios(ArrayList<Long> servicios) {
		this.servicios = servicios;
	}

}
