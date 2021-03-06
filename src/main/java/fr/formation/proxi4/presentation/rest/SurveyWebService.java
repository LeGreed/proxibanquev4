package fr.formation.proxi4.presentation.rest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.proxi4.metier.entity.Survey;
import fr.formation.proxi4.metier.service.SurveyService;

/**
 * Classe WebService pour les sondages de l'application.
 * 
 * @author Adminl
 *
 */
@RestController
@RequestMapping("/survey")
@Transactional(readOnly = true)
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class SurveyWebService {

	@Autowired
	private SurveyService service;

	/**
	 * Méthode envoyant le sondage en cours (sondage avec une date de début
	 * antérieure à la date du jour et pas de date de fermeture.)
	 * 
	 * @return Survey Le sondage en cours.
	 */
	@GetMapping
	public Survey getCurrentSurvey() {
		Survey survey = this.service.getCurrentSurvey();
		Hibernate.initialize(survey);
		return survey;
	}

	/** Méthode renvoyant le nombre de jours entre la date du jour et la fin prévisionnelle du sondage en cours.
	 * @return Integer le nombre de jours entre les deux dates.
	 */
	@GetMapping("/date")
	public Integer getDelay() {
		Integer days = 15;
		//Récupération de la date du jour.
		Survey survey = this.service.getCurrentSurvey();
		//between() renvoie un long, d'où le cast en int pour renvoyer le nombre de jours en Integer.
		days = (int) ChronoUnit.DAYS.between(LocalDate.now(), survey.getTempEndDate());
		return days;
	}
}
