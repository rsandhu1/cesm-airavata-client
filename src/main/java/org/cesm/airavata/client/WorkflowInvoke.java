package org.cesm.airavata.client;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.ExperimentAdvanceOptions;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.client.impl.HPCSettingsImpl;
import org.apache.airavata.client.impl.HostSchedulingSettingsImpl;
import org.apache.airavata.client.impl.NodeSettingsImpl;
import org.apache.airavata.client.tools.NameValuePairType;
import org.apache.airavata.workflow.model.wf.InvalidDataFormatException;
import org.apache.airavata.workflow.model.wf.WorkflowInput;
import org.apache.log4j.Logger;
import org.cesm.airavata.message.Message;
import org.cesm.airavata.utils.PropertyLoadingException;
import org.cesm.airavata.utils.ServiceConstants;


public class WorkflowInvoke extends AbstractClient{
	protected static Logger log = Logger.getLogger(WorkflowInvoke.class);
	
	public void runWorkflow(Message message) throws AiravataAPIInvocationException, URISyntaxException,PropertyLoadingException,InvalidDataFormatException{
		String experimentID = message.getExperimentID();
		String hostName = message.getHostName();
		int cpuCount = message.getProcessorCount();
		int nodeCount = message.getHostCount();
		int maxWallTime = message.getWalltime();
		String workflowName = message.getWorkflowName();
		String serviceName = message.getServiceName();
		String executionUser = message.getUserDN();
	
		log.info("Calling airavata client to run " + experimentID);
		String targetBaseLoc = getProperties().getProperty(ServiceConstants.OUTPUTLOCATION)+ File.separatorChar + message.getExperimentID();
		String username = getProperties().getProperty(ServiceConstants.AIRAVATA_USERNAME,"admin");
		ArrayList<String> inputs = new ArrayList<String>();
		List<String> input = message.getInputs();
		for (Iterator<String> iterator = input.iterator(); iterator.hasNext();) {
			inputs.add(iterator.next());
		}
		String queueName = message.getQueueName();
		List<NameValuePairType> list = new ArrayList<NameValuePairType>();
		
	
		
		ExperimentAdvanceOptions options = createOptions(workflowName, executionUser, username, experimentID, serviceName, hostName, cpuCount, nodeCount, maxWallTime, queueName,targetBaseLoc,list);
		runWorkflow( workflowName, inputs,options);
		
	}
	/**
	 * To run the workflow
	 * @param workflowName
	 * @param inputs
	 * @param options
	 * @return
	 * @throws AiravataAPIInvocationException
	 * @throws URISyntaxException
	 * @throws PropertyLoadingException
	 */
	public String runWorkflow(String workflowName,
			ArrayList<String> inputs, ExperimentAdvanceOptions options) throws AiravataAPIInvocationException, URISyntaxException,PropertyLoadingException, InvalidDataFormatException {
		AiravataAPI airavataAPI = getAiravataAPI();
		log.info("Submitting the request with airavata on " + airavataAPI.getAiravataManager().getWorkflowInterpreterServiceURL());
		
		List<WorkflowInput> workflowInputs = airavataAPI.getWorkflowManager().getWorkflowInputs(workflowName);
		int i = 0;
		for (String valueString : inputs) {
			workflowInputs.get(i).setValue(valueString);
			++i;
		}
		String experimentId = airavataAPI.getExecutionManager().runExperiment(workflowName, workflowInputs, options);
		log.info("Job submitted to airavata. Experiment ID: " + experimentId);
		
		return experimentId;
	}
	
	/**
	 * Generate context header for workflow
	 * @param workflowName
	 * @param username
	 * @param experimentName
	 * @param nodeName
	 * @param hostName
	 * @param cpuCount
	 * @param nodeCount
	 * @param maxWallTime
	 * @param queueName
	 * @return
	 * @throws AiravataAPIInvocationException
	 * @throws PropertyLoadingException
	 * @throws URISyntaxException
	 */
	public ExperimentAdvanceOptions createOptions(String workflowName, String executionUsername, String username, String experimentName, String nodeName, String hostName, int cpuCount, int nodeCount, int maxWallTime, String queueName, String outputLocation,List<NameValuePairType> list) throws AiravataAPIInvocationException, PropertyLoadingException, URISyntaxException{
		ExperimentAdvanceOptions options = super.getAiravataAPI().getExecutionManager().createExperimentAdvanceOptions(
				workflowName, username, null);
		
		if(executionUsername != null && !executionUsername.isEmpty()){
			options.setExperimentExecutionUser(executionUsername);
		}
		
		if(experimentName != null && !experimentName.isEmpty()){
		options.setCustomExperimentId(experimentName);
		}
		
		if (outputLocation != null && !outputLocation.isEmpty()){
		options.getCustomWorkflowOutputDataSettings().addNewOutputDataSettings(nodeName, outputLocation,
				null, false);
		}
		
		NodeSettingsImpl nodeSettings = new NodeSettingsImpl(nodeName);
		nodeSettings.setNameValuePair(list);
		HostSchedulingSettingsImpl hostSchedulingSettings = new HostSchedulingSettingsImpl();
		hostSchedulingSettings.setHostId(hostName);
		nodeSettings.setHostSettings(hostSchedulingSettings);
		HPCSettingsImpl hpcSettings = new HPCSettingsImpl();
		hpcSettings.setCPUCount(cpuCount);
		hpcSettings.setMaxWallTime(maxWallTime);
		hpcSettings.setNodeCount(nodeCount);
		hpcSettings.setQueueName(queueName);
		nodeSettings.setHPCSettings(hpcSettings);
		options.getCustomWorkflowSchedulingSettings().addNewNodeSettings(nodeSettings);
		return options;
	}

	
}
