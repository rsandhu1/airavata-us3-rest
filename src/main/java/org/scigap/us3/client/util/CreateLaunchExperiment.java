package org.scigap.us3.client.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.airavata.api.Airavata;
import org.apache.airavata.model.error.AiravataClientException;
import org.apache.airavata.model.error.AiravataSystemException;
import org.apache.airavata.model.error.InvalidRequestException;
import org.apache.airavata.model.util.ExperimentModelUtil;
import org.apache.airavata.model.workspace.experiment.AdvancedOutputDataHandling;
import org.apache.airavata.model.workspace.experiment.ComputationalResourceScheduling;
import org.apache.airavata.model.workspace.experiment.DataObjectType;
import org.apache.airavata.model.workspace.experiment.DataType;
import org.apache.airavata.model.workspace.experiment.Experiment;
import org.apache.airavata.model.workspace.experiment.UserConfigurationData;
import org.apache.thrift.TException;
import org.scigap.us3.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateLaunchExperiment {
	  private final static Logger logger = LoggerFactory.getLogger(CreateLaunchExperiment.class);
	  

	  public static String createUS3Experiment (Airavata.Client client, String experimentName ,String projectID,String applicationID,String resourceID, String xsedeProject, String outputDir, Message message) throws AiravataSystemException, InvalidRequestException, AiravataClientException, TException  {
	        try{
	            List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
	            DataObjectType input = new DataObjectType();
	            input.setKey("input");
	            input.setType(DataType.URI);
	            input.setValue(message.getBody().getInput().getParameters().get(0).getValue());
	            exInputs.add(input);

	            List<DataObjectType> exOut = new ArrayList<DataObjectType>();
	            DataObjectType output = new DataObjectType();
	            output.setKey("output");
	            output.setType(DataType.URI);
	            output.setValue("");
//	            DataObjectType output1 = new DataObjectType();
//	            output1.setKey("stdout");
//	            output1.setType(DataType.STDOUT);
//	            output1.setValue("");
//	            DataObjectType output2 = new DataObjectType();
//	            output2.setKey("stderr");
//	            output2.setType(DataType.STDERR);
//	            output2.setValue("");
	            exOut.add(output);
//	            exOut.add(output1);
//	            exOut.add(output2);
	            
	            Experiment simpleExperiment = ExperimentModelUtil.createSimpleExperiment(projectID, "ultrascan",experimentName , "US3AppTrestles", applicationID, exInputs);
	            simpleExperiment.setExperimentOutputs(exOut);

	            ComputationalResourceScheduling scheduling = ExperimentModelUtil.createComputationResourceScheduling(resourceID, message.getHeader().getProcessorcount(), message.getHeader().getHostcount(), 0,  message.getHeader().getQueuename(),  message.getHeader().getWalltime(), 0, 0,  xsedeProject);
	            UserConfigurationData userConfigurationData = new UserConfigurationData();
	            
	            scheduling.setResourceHostId(resourceID);
	            userConfigurationData.setAiravataAutoSchedule(false);
	            userConfigurationData.setOverrideManualScheduledParams(false);
	        
	            AdvancedOutputDataHandling dataHandling = new AdvancedOutputDataHandling();
	            dataHandling.setOutputDataDir(outputDir);
	            userConfigurationData.setAdvanceOutputDataHandling(dataHandling);
	        
	            userConfigurationData.setComputationalResourceScheduling(scheduling);
	            simpleExperiment.setUserConfigurationData(userConfigurationData);
	            return client.createExperiment(simpleExperiment);
	        } catch (AiravataSystemException e) {
	            logger.error("Error occured while creating the experiment...", e.getMessage());
	            throw new AiravataSystemException(e);
	        } catch (InvalidRequestException e) {
	            logger.error("Error occured while creating the experiment...", e.getMessage());
	            throw new InvalidRequestException(e);
	        } catch (AiravataClientException e) {
	            logger.error("Error occured while creating the experiment...", e.getMessage());
	            throw new AiravataClientException(e);
	        }catch (TException e) {
	            logger.error("Error occured while creating the experiment...", e.getMessage());
	            throw new TException(e);
	        }
	    }
}
