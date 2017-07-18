package dev.console;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;
import dev.service.CalculService;

public class AppTest {
	private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	@Rule
	public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();
	@Rule
	public ExpectedException exceptionLevee = ExpectedException.none();
	private App app;
	private CalculService calculService;

	@Before
	public void setUp() throws Exception {
		this.calculService = mock(CalculService.class);
		this.app = new App(new Scanner(System.in), calculService);
	}

	@Test
	public void testAfficherTitre() throws Exception {
		this.app.afficherTitre();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("**** Application Calculatrice ****");
	}

	@Test

	public void testEvaluer() throws Exception {

		LOG.info("Etant donné, un service CalculService qui retourne 35 à l'évaluation de l'expression 1+34");
		String expression = "1+34";
		when(calculService.additionner(expression)).thenReturn(35);

		LOG.info("Lorsque la méthode evaluer est invoquée");
		this.app.evaluer(expression);

		LOG.info("Alors le service est invoqué avec l'expression {}", expression);
		verify(calculService).additionner(expression);

		LOG.info("Alors dans la console, s'affiche 1+34=35");
		assertThat(systemOutRule.getLog()).contains("1+34=35");

	}

	@Test(expected = CalculException.class)
	public void testEvaluerException() throws Exception {
		LOG.info("Etant donné, un service CalculService qui retourne 35 à l'évaluation de l'expression 1+34");
		String expression = "1-34";
		when(calculService.additionner(expression)).thenThrow(CalculException.class);
		LOG.info("Lorsque la méthode evaluer est invoquée");
		this.app.evaluer(expression);
		exceptionLevee.expect(CalculException.class);
		exceptionLevee.expectMessage("L'expression " + expression + " est invalide");

	}

	@Test
	public void testFinEtape1() {
		systemInMock.provideLines("fin");
		app.demarrer();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("Au revoir :-(");
	}

	@Test
	public void testFinEtape2() {
		systemInMock.provideLines("1+2", "fin");
		when(calculService.additionner("1+2")).thenReturn(3);
		app.demarrer();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("1+2=3", "Au revoir :-(");
	}

	@Test
	public void testFinEtape3() {
		systemInMock.provideLines("AAAA", "fin");
		when(calculService.additionner("AAAA")).thenThrow(CalculException.class);
		exceptionLevee.expect(CalculException.class);
		exceptionLevee.expectMessage("L'expression AAAA est invalide");
		app.demarrer();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("1+2=3", "Au revoir :-(");
	}

	@Test
	public void testFinEtape4() {
		systemInMock.provideLines("1+2", "30+2", "fin");
		when(calculService.additionner("1+2")).thenReturn(3);
		when(calculService.additionner("30+2")).thenReturn(32);
		app.demarrer();
		String logConsole = systemOutRule.getLog();
		assertThat(logConsole).contains("1+2=3", "Au revoir :-(");
	}

}