package it.prova.gestionesatelliti.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@GetMapping
	public ModelAndView listaAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("edit_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid @ModelAttribute("edit_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if ((satellite.getDataRientro() == null && satellite.getStato().equals(StatoSatellite.DISATTIVATO))
				|| satellite.getDataLancio().after(satellite.getDataRientro())) {
			result.rejectValue("dataLancio", "dataLancio.dataRientro.rangeInValue");
			result.rejectValue("dataRientro", "dataRientro.dataLancio.rangeInValue");
		}

		if (result.hasErrors())
			return "satellite/edit";

		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/delete/{idSatellite}")
	public String prepareDelete(@PathVariable(required = true) Long idSatellite, Model model,
			RedirectAttributes redirectAttrs) {

		Satellite satelliteTest = satelliteService.caricaSingoloElemento(idSatellite);
		if (satelliteTest.getDataLancio().before(new Date()) || satelliteTest.getDataRientro().after(new Date())) {
			redirectAttrs.addFlashAttribute("erroreMessage", "Operazione non eseguita correttamente");
			return "redirect:/satellite";
		}
		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/delete";
	}

	@GetMapping("/remove/{idSatellite}")
	public String confirm(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		satelliteService.rimuoviById(idSatellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/lanciatiDa2Anni")
	public String listLanciatiDa2Anni(ModelMap model) {
		List<Satellite> results = satelliteService.findLanciatiDa2Anni();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/disattivatiInOrbita")
	public String listDisattivatiInOrbita(ModelMap model) {
		List<Satellite> results = satelliteService.findDisattivatiInOrbita();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/fissiDa10Anni")
	public String listFissiDa10Anni(ModelMap model) {
		List<Satellite> results = satelliteService.findFissiDa10Anni();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/lancia/{idSatellite}")
	public String lancia(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		Satellite satellitaInLancio = satelliteService.caricaSingoloElemento(idSatellite);

		if (satellitaInLancio.getDataLancio() != null || satellitaInLancio.getDataRientro() != null) {
			redirectAttrs.addFlashAttribute("erroreMessage", "Operazione non eseguita correttamente");
			return "redirect:/satellite";
		}

		satellitaInLancio.setDataLancio(new Date());
		satellitaInLancio.setStato(StatoSatellite.IN_MOVIMENTO);
		satelliteService.aggiorna(satellitaInLancio);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/rientra/{idSatellite}")
	public String rientra(@PathVariable(required = true) Long idSatellite, RedirectAttributes redirectAttrs) {

		Satellite satellitaInRientro = satelliteService.caricaSingoloElemento(idSatellite);

		if (satellitaInRientro.getDataRientro() != null || satellitaInRientro.getDataLancio() == null) {
			redirectAttrs.addFlashAttribute("erroreMessage", "Operazione non eseguita correttamente");
			return "redirect:/satellite";
		}

		satellitaInRientro.setDataRientro(new Date());
		satellitaInRientro.setStato(StatoSatellite.DISATTIVATO);
		satelliteService.aggiorna(satellitaInRientro);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

}