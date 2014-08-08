package org.scigap.us3.client.rest;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.scigap.us3.client.util.ClientConstants;
import org.scigap.us3.client.util.PropertyUtils;
import org.scigap.us3.message.MessageResponse;


@Path("/job/jobstatus")
public class AiravataJobStatus extends AbstractService {

	private static Properties properties;
	
	public AiravataJobStatus() {
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
//			ExperimentData data =  airavataAPI.getProvenanceManager().getExperimentData(experimentID);

            StringBuffer buffer = new StringBuffer();
//			if(data.getNodeDataList().size() > 0){
//				response.setStatus(data.getNodeData(serviceName).getState().toString());
//			}else{
//				response.setStatus(data.getState().toString());
//            }
//            String status = response.getStatus();
//            response.setStatus(status);
//            List<ApplicationJob> applicationJobs = airavataAPI.getProvenanceManager().getApplicationJobs(experimentID, null, null);
//            if (applicationJobs.size() != 0) {
//                ApplicationJob job = applicationJobs.get(0);
//                buffer.append(job.getJobData());
//            }
//
//			if (status.equals("FAILED")) {
//				List<ExecutionError> experimentExecutionErrors = airavataAPI.getExecutionManager().getExecutionErrors(experimentID, experimentID, serviceName, null, null);
//				for (ExecutionError experimentExecutionError : experimentExecutionErrors) {
//					buffer.append(experimentExecutionError.getErrorMessage());
//				}
//			}
            //as per the requested by Gary removing these content from message Element
//            else if(status.equals("FINISHED")){
//				List<OutputData> outputData = data.getNodeData(serviceName).getOutputData();
//				for (OutputData outputData2 : outputData) {
//					buffer.append(outputData2.getValue());
//				}
//			}
            response.setMessage(buffer.toString());
        }catch(Exception e){
			response.setStatus("FAILED");
			response.setMessage(e.getMessage());
		} 
		return response;
	}
	public static Properties getProperties() throws Exception {
		if(null == properties){
			properties = PropertyUtils.load(ClientConstants.PROPERTYFILE_NAME);
		}
		return properties;
	}
}
