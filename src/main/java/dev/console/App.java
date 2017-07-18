package dev.console;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;
import dev.service.CalculService;

/**
 * Hello world!
 *
 */
public class App {
	private Scanner scanner;
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	private CalculService calculatrice;

	public App(Scanner scanner, CalculService calculatrice) {

		this.scanner = scanner;

		this.calculatrice = calculatrice;

	}

	protected void afficherTitre() {
		LOG.info("**** Application Calculatrice ****");
	}

	public void demarrer() throws CalculException {

		afficherTitre();
		Scanner scan = new Scanner(System.in);
		String saisie = scan.next();
		while (!saisie.equalsIgnoreCase("fin")) {
			try {
				evaluer(saisie);
				saisie = scan.next();
			} catch (Exception e) {
				throw new CalculException("L'expression " + saisie + " est invalide");
			}

		}
		LOG.info("Au revoir :-(");
		scan.close();

	}

	protected void evaluer(String expression) throws CalculException {
		try {
			LOG.info(expression + "=" + calculatrice.additionner(expression));
		} catch (Exception e) {
			LOG.info("L'expression " + expression + " est invalide");
		}

	}
}
