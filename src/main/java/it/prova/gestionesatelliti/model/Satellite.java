package it.prova.gestionesatelliti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "satellite")
public class Satellite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "{denominazione.notblank}")
	@Column(name = "denominazione")
	private String denominazione;

	@NotBlank(message = "{codice.notblank}")
	@Column(name = "codice")
	private String codice;

	@Column(name = "datalancio")
	private Date dataLancio;

	@Column(name = "datarientro")
	private Date dataRientro;

	@Column(name = "stato")
	@Enumerated(EnumType.STRING)
	private StatoSatellite stato;

	public Satellite() {
		super();
	}

	public Satellite(@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotBlank(message = "{codice.notblank}") String codice) {
		super();
		this.denominazione = denominazione;
		this.codice = codice;
	}

	public Satellite(@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotBlank(message = "{codice.notblank}") String codice, Date dataLancio) {
		super();
		this.denominazione = denominazione;
		this.codice = codice;
		this.dataLancio = dataLancio;
	}

	public Satellite(@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotBlank(message = "{codice.notblank}") String codice, Date dataLancio, Date dataRientro) {
		super();
		this.denominazione = denominazione;
		this.codice = codice;
		this.dataLancio = dataLancio;
		this.dataRientro = dataRientro;
	}

	public Satellite(@NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotBlank(message = "{codice.notblank}") String codice, Date dataLancio, Date dataRientro,
			StatoSatellite stato) {
		super();
		this.denominazione = denominazione;
		this.codice = codice;
		this.dataLancio = dataLancio;
		this.dataRientro = dataRientro;
		this.stato = stato;
	}

	public Satellite(Long id, @NotBlank(message = "{denominazione.notblank}") String denominazione,
			@NotBlank(message = "{codice.notblank}") String codice, Date dataLancio, Date dataRientro,
			StatoSatellite stato) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.codice = codice;
		this.dataLancio = dataLancio;
		this.dataRientro = dataRientro;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getDataLancio() {
		return dataLancio;
	}

	public void setDataLancio(Date dataLancio) {
		this.dataLancio = dataLancio;
	}

	public Date getDataRientro() {
		return dataRientro;
	}

	public void setDataRientro(Date dataRientro) {
		this.dataRientro = dataRientro;
	}

	public StatoSatellite getStato() {
		return stato;
	}

	public void setStato(StatoSatellite stato) {
		this.stato = stato;
	}

}
