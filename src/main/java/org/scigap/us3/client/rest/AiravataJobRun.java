package org.scigap.us3.client.rest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.scigap.us3.client.util.ConnectionManager;
import org.scigap.us3.client.util.MessageUtil;
import org.scigap.us3.client.util.ServiceConstants;
import org.scigap.us3.message.Message;
import org.scigap.us3.message.MessageResponse;
import org.scigap.us3.message.Parameters;

import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;

import cryptix.util.mime.Base64InputStream;


@Path("/job/runjob")
public class AiravataJobRun extends AbstractService{
	
	public AiravataJobRun() throws Exception{
		super();
	}

	@Path("/sync")
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public MessageResponse submitJobSync(Message message) {
		if (message.getHeader().getExperimentid() == null || message.getHeader().getExperimentid().isEmpty()) {
			String newexpID = createExperimentID();
			message.getHeader().setExperimentid(newexpID);
		}
		String expID = message.getHeader().getExperimentid();
		MessageResponse response = new MessageResponse();
		String messageXML = MessageUtil.readRequestMessage(message);
		log.info("Sync Resuest recived from client at :" + new Date() + " : and assigned experiment id :" + expID
				+ "with message" + messageXML);
		try {
			insertExperimentStatus(expID, ServiceConstants.SUBMITTED, messageXML, message.getHeader().getCluster(),
					message.getHeader().getUs3Db());
			submitSyncRequest(message);
		} catch (Exception e) {
			try {
				updateExperimentStatus(expID, ServiceConstants.FAILED, e.getLocalizedMessage());
				response.setMessage("Failed to submit job to gfac");
			} catch (Exception e1) {
				response.setStatus(ServiceConstants.ERROR);
				response.setMessage(e1.getLocalizedMessage());
			}
			response.setStatus(ServiceConstants.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}
		response.setExperimentid(expID);
		return response;
	}

	@Path("/async")
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public MessageResponse submitJobAsync(Message message) {
		if (message.getHeader().getExperimentid() == null || message.getHeader().getExperimentid().isEmpty()) {
			String newexpID = createExperimentID();
			message.getHeader().setExperimentid(newexpID);
		}
		String expID = message.getHeader().getExperimentid();

		message.getHeader().setExperimentid(expID);
		MessageResponse response = new MessageResponse();
		String messageXML = MessageUtil.readRequestMessage(message);

		log.info("Async Resuest recived from client at :" + new Date() + " : and assigned experiment id :" + expID
				+ "with message" + messageXML);
		try {
			insertExperimentStatus(expID, ServiceConstants.SUBMITTED, messageXML, message.getHeader().getCluster(),
					message.getHeader().getUs3Db());
			submitAsyncRequest(message);
		} catch (Exception e) {
			try {
				updateExperimentStatus(expID, ServiceConstants.FAILED, e.getLocalizedMessage());
				response.setMessage("Failed to submit job to gfac" + e.getLocalizedMessage());
			} catch (Exception e1) {
				response.setStatus(ServiceConstants.ERROR);
				response.setMessage(e1.getLocalizedMessage());
			}
			response.setStatus(ServiceConstants.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}
		response.setExperimentid(expID);
		return response;
	}

	@Path("/resubmitasync")
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public MessageResponse submitRecoverJobAsync(Message message) {
		MessageResponse response = new MessageResponse();
		String messageXML = MessageUtil.readRequestMessage(message);
		String expID = message.getHeader().getExperimentid();
		log.info("Async Resuest recived from client at :" + new Date() + " : and assigned experiment id :" + expID
				+ "with message " + messageXML);
		try {
			submitAsyncRequest(message);
		} catch (Exception e) {
			try {
				response.setMessage("Failed to submit job to gfac");
			} catch (Exception e1) {
				response.setStatus(ServiceConstants.ERROR);
				response.setMessage(e1.getLocalizedMessage());
			}
			response.setStatus(ServiceConstants.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}
		response.setExperimentid(message.getHeader().getExperimentid());
		return response;
	}

	@Path("/async")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/xml")
	public MessageResponse submitJobAsync(MultiPart multiPart) {
		String messageXML = null;
		String expID = null;
		MessageResponse response = new MessageResponse();
		try {
			Message message = readMulipartData(multiPart);
			expID = message.getHeader().getExperimentid();
			messageXML = MessageUtil.readRequestMessage(message);
			log.info("Async multipart resuest recived from client at :" + new Date()
					+ " : and assigned experiment id :" + message.getHeader().getExperimentid() + "with message "
					+ messageXML);
			insertExperimentStatus(expID, ServiceConstants.SUBMITTED, messageXML, message.getHeader().getCluster(),
					message.getHeader().getUs3Db());
			submitAsyncRequest(message);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				updateExperimentStatus(expID, ServiceConstants.FAILED, "Failed to submit job :" + e.getMessage());
				response.setMessage("Failed to submit job to gfac" + e.getMessage());
			} catch (Exception e1) {
				response.setStatus(ServiceConstants.ERROR);
				response.setMessage(e.getLocalizedMessage());
			}
			response.setStatus(ServiceConstants.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}
		response.setExperimentid(expID);
		return response;
	}

	@Path("/sync")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/xml")
	public MessageResponse submitJobSync(MultiPart multiPart) {
		String expID = null;
		String messageXML = null;
		MessageResponse response = new MessageResponse();
		try {
			Message message = readMulipartData(multiPart);
			expID = message.getHeader().getExperimentid();
			messageXML = MessageUtil.readRequestMessage(message);
			log.info("Sync multipart resuest recived from client at :" + new Date() + " : and assigned experiment id :"
					+ message.getHeader().getExperimentid() + "with message " + messageXML);
			insertExperimentStatus(expID, ServiceConstants.SUBMITTED, messageXML, message.getHeader().getCluster(),
					message.getHeader().getUs3Db());
			submitSyncRequest(message);
		} catch (Exception e) {
			try {
				updateExperimentStatus(expID, ServiceConstants.FAILED, "Failed to submit job :" + e.getMessage());
				response.setMessage("Failed to submit job to gfac" + e.getMessage());
			} catch (Exception e1) {
				response.setStatus(ServiceConstants.ERROR);
				response.setMessage(e.getLocalizedMessage());
			}
			response.setStatus(ServiceConstants.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}
		response.setExperimentid(expID);
		return response;
	}

	private void submitSyncRequest(Message message) throws Exception {
//		UltrascanRun client = new UltrascanRun();
		try {
//			client.runWorkflow(message);
		} catch (Exception e) {
			throw e;
		}
	}

	private void submitAsyncRequest(final Message message) throws Exception {
		log.info("Async call to run job");
		JobRunner jobRunner = new JobRunner(message);
		ExecutorService pool = Executors.newFixedThreadPool(1);
		try {
			Future<String> future = pool.submit(jobRunner);
			future.get();
		} catch (Exception e) {
			throw e;
		} finally {
			pool.shutdown();
		}
	}

	private Message readMulipartData(MultiPart multiPart) throws Exception {
		Message message = multiPart.getBodyParts().get(0).getEntityAs(Message.class);
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try{
		if (message.getHeader().getExperimentid() == null || message.getHeader().getExperimentid().isEmpty()) {
			String newexpID = createExperimentID();
			message.getHeader().setExperimentid(newexpID);
		}
		String expID = message.getHeader().getExperimentid();

		BodyPartEntity entity = (BodyPartEntity) multiPart.getBodyParts().get(1).getEntity();
		List<String> list = multiPart.getBodyParts().get(1).getHeaders().get("Content-Transfer-Encoding");
		if (list != null && list.contains("base64")) {
			log.info("Encoded data recieved from the server");
			inputStream = new Base64InputStream(entity.getInputStream());
		} else {
			log.info("Data recieved from the server");
			inputStream = entity.getInputStream();
		}
		String inputLocation = properties.getProperty(ServiceConstants.INPUTLOCATION);
		if (inputLocation == null) {
			inputLocation = ".";
		}
		File file = new File(inputLocation + File.separatorChar + expID);
		log.info("Input Location " + file.getAbsolutePath());
		if (file != null && !file.exists()) {
			file.mkdir();
		}
		File tarfile = new File(file.getAbsolutePath() + File.separator + "hpcinput.tar");
		outputStream = new FileOutputStream(tarfile);
		int i;
		while ((i = inputStream.read()) != -1) {
			outputStream.write(i);
		}
		String filePath = "file://" + tarfile.getAbsolutePath();
		Parameters parameters = new Parameters();
		parameters.setName("inputData");
		parameters.setValue(filePath);
		message.getBody().getInput().getParameters().add(parameters);
		writeJobXML(expID,message);
		}
		finally{
			if (multiPart != null) {
				multiPart.cleanup();
				multiPart.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}
		return message;
	}

	private synchronized String createExperimentID() {

		return "US3-Experiment-" + UUID.randomUUID();
	}

	private void insertExperimentStatus(String experimentID, String status, String requestXML, String hostname,
			String us3DB) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionManager.getConnection(ServiceConstants.JDBC_DRIVER,
					properties.getProperty(ServiceConstants.PROPERTY_JDBC_URL));
			statement = connection.prepareStatement(ServiceConstants.INSERT_NOTIFICATION_INFO);
			statement.setString(1, experimentID);
			statement.setString(2, status);
			statement.setString(3, requestXML);
			statement.setString(4, hostname);
			statement.setString(5, us3DB);
			statement.executeUpdate();
		} finally {
			if(statement != null){
			statement.close();
			}
			if(connection != null){
			connection.close();
			}
		}

	}

	private void updateExperimentStatus(String experimentID, String status, String description) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionManager.getConnection(ServiceConstants.JDBC_DRIVER,
					properties.getProperty(ServiceConstants.PROPERTY_JDBC_URL));
			statement = connection.prepareStatement(ServiceConstants.UPDATE_NOTIFICATION_INFO);
			statement.setString(1, status);
			statement.setString(2, description);
			statement.setString(3, experimentID);
			statement.executeUpdate();
		} finally {
			if(statement != null){
				statement.close();
				}
				if(connection != null){
				connection.close();
				}
		}

	}

	private void writeJobXML(String experimentID, Message message) throws Exception {
		try {
			String inputLocation = properties.getProperty(ServiceConstants.INPUTLOCATION);
			if (inputLocation == null) {
				inputLocation = ".";
			}
			File file = new File(inputLocation + File.separatorChar + experimentID);
			log.info("Input Location " + file.getAbsolutePath());
			if (file != null && !file.exists()) {
				file.mkdir();
			}
			File jobFile = new File(file.getAbsolutePath() + File.separator + "jobxmlfile.xml");
			String filePath = "file://" + jobFile.getAbsolutePath();
			Parameters parameters = new Parameters();
			parameters.setName("jobxml");
			parameters.setValue(filePath);
			message.getBody().getInput().getParameters().add(parameters);

			log.info("Job File Location " + jobFile.getAbsolutePath());
			BufferedWriter out = new BufferedWriter(new FileWriter(jobFile));
			out.write(MessageUtil.readRequestMessage(message));
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	class JobRunner implements Callable<String> {
		Message message;

		protected JobRunner(Message message) {
			this.message = message;
		}

		public String call() throws Exception {
//			UltrascanRun client = new UltrascanRun();
			try {
//				client.runWorkflow(message);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return "OK";
		}

	}
}
