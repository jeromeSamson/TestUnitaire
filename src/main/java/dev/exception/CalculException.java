package dev.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculException extends NumberFormatException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CalculException.class);

	public CalculException(String s) {
		super(s);
		LOG.debug(s);
	}

}
