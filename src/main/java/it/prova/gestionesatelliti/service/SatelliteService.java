package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	
	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);
	
	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuovi(Satellite satelliteInstance);
	
	public void rimuoviById(Long satelliteInstance);
	
	public List<Satellite> findByExample(Satellite example);

	public List<Satellite> findLanciatiDa2Anni();

	public List<Satellite> findDisattivatiInOrbita();

	public List<Satellite> findFissiDa10Anni();

}
