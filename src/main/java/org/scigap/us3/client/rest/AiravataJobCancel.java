package org.scigap.us3.client.rest;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.airavata.model.workspace.experiment.JobState;
import org.apache.airavata.model.workspace.experiment.JobStatus;
import org.scigap.us3.client.util.ClientConstants;
import org.scigap.us3.client.util.PropertyUtils;
import org.scigap.us3.client.util.ServiceConstants;
import org.scigap.us3.message.MessageResponse;

@Path("/job/canceljob")
public class AiravataJobCancel extends AbstractService{

	public AiravataJobCancel() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Properties properties;

	@GET
	@Path("{experimentid}")
	@Produces("application/xml")
	public MessageResponse cancelRunningJob(@PathParam("experimentid") String experimentID) {
		MessageResponse messageResponse = new MessageResponse();
		try {
			Map<String, JobStatus> jobStatuses  = getJobData(experimentID);
			Set<String> strings = jobStatuses.keySet();
            for (String key : strings) {
                JobStatus jobStatus = jobStatuses.get(key);
             if(jobStatus == null){
            	 throw new Exception("Experiment " + experimentID + " not found");
             } else if(JobState.COMPLETE.equals(jobStatus.getJobState())){
            	 throw new Exception("Experiment " + experimentID + " COMPLETED");
             }else{
//            	 MyProxyManager myProxyManager = new MyProxyManager(properties);
//                 GlobusCredential credential = myProxyManager.getGlobusCredential();
//                 log.info("jobURL: " + jobURL);
//                 GramJob job = new GramJob("");
//                 job.setID(jobURL);
//                 job.setCredentials(new GlobusGSSCredentialImpl(credential,
//                         GSSCredential.INITIATE_AND_ACCEPT));
//                 job.cancel();
                 messageResponse.setStatus("CANCELED");
                 log.info("Cancelled Job " + experimentID);
     		
             }
            }
           } catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			messageResponse.setStatus(ServiceConstants.ERROR);
			messageResponse.setMessage(e.getLocalizedMessage());
		}
		return messageResponse;
	}

	private Map<String, JobStatus> getJobData(String experimentID) throws Exception {
		try {
			Map<String, JobStatus> jobStatuses = airavata.getJobStatuses(experimentID);
			return jobStatuses;
		} catch (Exception e) {
			throw e;
		}
	}

	public static Properties getProperties() throws Exception {
		if (null == properties) {
			properties = PropertyUtils.loadProperty(ClientConstants.PROPERTYFILE_NAME);
		}
		return properties;
	}

}
