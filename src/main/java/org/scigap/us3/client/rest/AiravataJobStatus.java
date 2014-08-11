package org.scigap.us3.client.rest;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.airavata.model.workspace.experiment.JobStatus;
import org.scigap.us3.client.util.ClientConstants;
import org.scigap.us3.client.util.PropertyUtils;
import org.scigap.us3.message.MessageResponse;


@Path("/job/jobstatus")
public class AiravataJobStatus extends AbstractService {

	private static Properties properties;
	
	public AiravataJobStatus() throws Exception{
		super();
	}

	@GET
	@Path("{experimentid}")
	@Produces("application/xml")
	public MessageResponse gramJobStatus(@PathParam("experimentid") String experimentID) {
		log.info("Getting Status for ExperimentID : " + experimentID);
		MessageResponse response = null;
		try {
			response = getJobStatus(experimentID);
		} catch (Exception e) {
			response.setExperimentid(experimentID);
			response.setStatus(Response.Status.BAD_REQUEST.toString());
			response.setMessage(e.getLocalizedMessage());
		}
		return response;
	}

	private MessageResponse getJobStatus(String experimentID) throws Exception {
		MessageResponse response = new MessageResponse();
		try {
            StringBuffer buffer = new StringBuffer();
            Map<String, JobStatus> jobStatuses = airavata.getJobStatuses(experimentID);
            Set<String> strings = jobStatuses.keySet();
            for (String key : strings) {
                JobStatus jobStatus = jobStatuses.get(key);
                if(jobStatus == null){
                	buffer.append(airavata.getExperimentStatus(experimentID));
                }else{
                buffer.append(jobStatus.getJobState().toString());	
                }
                }
            response.setMessage(buffer.toString());
        }catch(Exception e){
			response.setStatus("FAILED");
			response.setMessage(e.getMessage());
		} 
		return response;
	}
	public static Properties getProperties() throws Exception {
		if(null == properties){
			properties = PropertyUtils.loadProperty(ClientConstants.PROPERTYFILE_NAME);
		}
		return properties;
	}
}
