package org.cesm.airavata.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyLoadingException extends Exception {
	private static final Logger log = LoggerFactory.getLogger(PropertyLoadingException.class);

	private static final long serialVersionUID = 1L;

	public PropertyLoadingException(Throwable e) {
		super("Error loading properties", e);
		log.error(e.getLocalizedMessage(), e);
	}

	public PropertyLoadingException(String message) {
		super(message, null);
		log.error(message);
	}

	public PropertyLoadingException(String message, Throwable e) {
		super(message, e);
		log.error(message, e);
	}

}
