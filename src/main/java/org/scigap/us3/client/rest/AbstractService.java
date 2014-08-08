package org.scigap.us3.client.rest;

import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.scigap.us3.client.util.PropertyUtils;

public abstract class AbstractService {
	protected Properties properties;
	protected static Logger log;

	public AbstractService() {
	    log = Logger.getLogger(getClass());
		properties = PropertyUtils.getProps();
		log.debug("Loaded properties from file.");
		if(properties == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
