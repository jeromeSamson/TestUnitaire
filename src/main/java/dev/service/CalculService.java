package dev.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.exception.CalculException;

public class CalculService {
	private static final Logger LOG = LoggerFactory.getLogger(CalculService.class);

	public int additionner(String expression) throws CalculException {
		LOG.info("Evaluation de l'expression : " + expression);
		try {
			return Stream.of(expression.split("\\+")).map(p -> Integer.parseInt(p)).reduce(((p, p1) -> p + p1)).get();
		} catch (RuntimeException e) {
			throw new CalculException("Erreur sur l'expression");
		}
	}
}
