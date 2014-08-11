package org.scigap.us3.client.rest;

import java.util.Properties;

import javax.ws.rs.Path;

import org.scigap.us3.client.util.ClientConstants;
import org.scigap.us3.client.util.PropertyUtils;

@Path("/job/canceljob")
public class AiravataJobCancel extends AbstractService{

	public AiravataJobCancel() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Properties properties;

//	@GET
//	@Path("{experimentid}")
//	@Produces("application/xml")
//	public MessageResponse cancelRunningJob(@PathParam("experimentid") String experimentID) {
//		MessageResponse messageResponse = new MessageResponse();
//		try {
//            String jobURL = getJobData(experimentID).getJobId();
//            MyProxyManager myProxyManager = new MyProxyManager(properties);
//            GlobusCredential credential = myProxyManager.getGlobusCredential();
//            log.info("jobURL: " + jobURL);
//            GramJob job = new GramJob("");
//            job.setID(jobURL);
//            job.setCredentials(new GlobusGSSCredentialImpl(credential,
//                    GSSCredential.INITIATE_AND_ACCEPT));
//            job.cancel();
//            messageResponse.setStatus("CANCELED");
//            log.info("Cancelled Job " + experimentID);
//		} catch (Exception e) {
//			log.error(e.getLocalizedMessage(), e);
//			messageResponse.setStatus(ServiceConstants.ERROR);
//			messageResponse.setMessage(e.getLocalizedMessage());
//		}
//		return messageResponse;
//	}
//
//	private ApplicationJob getJobData(String experimentID) throws Exception {
//		try {
//			PasswordCallback passwordCallback = new PasswordCallbackImpl();
//			String username = getProperties().getProperty(ClientConstants.AIRAVATA_USERNAME, "admin");
//			String registryURL = getProperties().getProperty(ClientConstants.AIRAVATA_REGISTRYURL, null);
//			String gateway = getProperties().getProperty(ClientConstants.AIRAVATA_GATEWAYNAME, "default");
//			String workflowName = getProperties().getProperty(ClientConstants.US3_WORKFLOWNAME, "US3_Service");
//			String serviceName = getProperties().getProperty(ClientConstants.US3_GFAC_SERVICE, "US3_Service_invoke");
//
//			AiravataAPI airavataAPI = AiravataAPIFactory.getAPI(new URI(registryURL), gateway, username,
//					passwordCallback);
//
//			ExperimentData data = airavataAPI.getProvenanceManager().getExperimentData(experimentID);
//			StringBuffer buffer = new StringBuffer();
//			if (data.getNodeDataList().size() > 0) {
//				buffer.append(data.getNodeData(serviceName).getState());
//			} else {
//				buffer.append(data.getState());
//			}
//			String status = buffer.toString();
//
//			List<ApplicationJob> gFacJoblist = airavataAPI.getProvenanceManager().getApplicationJobs(experimentID, experimentID,
//					serviceName);
//			for (ApplicationJob gFacJob2 : gFacJoblist) {
//				return gFacJob2;
//			}
//
//		} catch (Exception e) {
//			throw e;
//		}
//		return null;
//	}

	public static Properties getProperties() throws Exception {
		if (null == properties) {
			properties = PropertyUtils.loadProperty(ClientConstants.PROPERTYFILE_NAME);
		}
		return properties;
	}

}
