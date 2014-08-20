package org.scigap.us3.client.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.airavata.model.workspace.experiment.ErrorDetails;
import org.apache.airavata.model.workspace.experiment.Experiment;
import org.apache.airavata.model.workspace.experiment.ExperimentState;
import org.apache.airavata.model.workspace.experiment.ExperimentStatus;
import org.apache.airavata.model.workspace.experiment.JobState;
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
            Map<String, JobStatus> jobStatuses = airavata.getJobStatuses(experimentID);
            Set<String> strings = jobStatuses.keySet();
            for (String key : strings) {
                JobStatus jobStatus = jobStatuses.get(key);
                if(jobStatus == null || JobState.COMPLETE.equals(jobStatus.getJobState())|| JobState.FAILED.equals(jobStatus.getJobState())){
                	Experiment experiment = airavata.getExperiment(experimentID);
                	ExperimentStatus experimentStatus = experiment.getExperimentStatus();
                	response.setStatus(experimentStatus.getExperimentState().toString());
                	if(ExperimentState.FAILED.equals(experimentStatus)){
                		List<ErrorDetails> errors = experiment.getErrors();
                		
                		response.setMessage();
                	}else{
                	response.setMessage("Status last update: "+ new Date(experimentStatus.getTimeOfStateChange()).toLocaleString());
                	}
                }
                else{
                 	response.setStatus(jobStatus.getJobState().toString());
                	response.setMessage("Status last update: " + new Date(jobStatus.getTimeOfStateChange()).toLocaleString());
                }
                }
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
