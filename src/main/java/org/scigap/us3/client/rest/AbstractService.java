package org.scigap.us3.client.rest;

import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.airavata.api.Airavata;
import org.apache.airavata.api.client.AiravataClientFactory;
import org.apache.airavata.common.utils.AiravataUtils;
import org.apache.log4j.Logger;
import org.scigap.us3.client.util.PropertyUtils;

public abstract class AbstractService {
	public static final String THRIFT_SERVER_HOST = "gridfarm005.ucs.indiana.edu";
	public static final int THRIFT_SERVER_PORT = 8930;
	   
	protected Properties properties;
	protected static Logger log;
	protected Airavata.Client airavata;

	public AbstractService() throws Exception{
	    log = Logger.getLogger(getClass());
		properties = PropertyUtils.getProps();
		log.debug("Loaded properties from file.");
		if(properties == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		AiravataUtils.setExecutionAsClient();
			airavata = AiravataClientFactory.createAiravataClient(THRIFT_SERVER_HOST, THRIFT_SERVER_PORT);
	}
}
