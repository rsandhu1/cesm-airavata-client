package org.cesm.airavata.client;

import java.net.URI;
import java.util.List;

import org.apache.airavata.client.AiravataAPIFactory;
import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.registry.api.PasswordCallback;
import org.apache.airavata.registry.api.ExecutionErrors.Source;
import org.apache.airavata.registry.api.workflow.ApplicationJob;
import org.apache.airavata.registry.api.workflow.ExecutionError;
import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.cesm.airavata.utils.PasswordCallbackImpl;
import org.cesm.airavata.utils.ServiceConstants;
;

public class MonitorWorkflow extends AbstractClient{

	public String getJobStatus(String experimentID) throws Exception {
		StringBuffer buffer = new StringBuffer();
	    try {
			PasswordCallback passwordCallback = new PasswordCallbackImpl();

			String username = getProperties().getProperty(ServiceConstants.AIRAVATA_USERNAME,"admin");
			String registryURL = getProperties().getProperty(ServiceConstants.AIRAVATA_REGISTRYURL,null);
			String gateway = getProperties().getProperty(ServiceConstants.AIRAVATA_GATEWAYNAME,"default");
			AiravataAPI airavataAPI = AiravataAPIFactory.getAPI(new URI(registryURL), gateway, username,
					passwordCallback);
			
			
			ExperimentData data =  airavataAPI.getProvenanceManager().getExperimentData(experimentID);

           
            buffer.append(data.getState().toString());
               
			List<ApplicationJob> applicationJobs = airavataAPI.getProvenanceManager().getApplicationJobs(experimentID, null, null);
			String status = null;
			if(applicationJobs != null && applicationJobs.size() >0){
				buffer.append(applicationJobs.get(0).getStatus().toString());
			}
			if (applicationJobs.size() != 0 && !status.equals("FAILED")) {
                ApplicationJob job = applicationJobs.get(0);
                buffer.append(job.getJobData());
            }
        	if (status.equals("FAILED")) {
				List<ExecutionError> experimentExecutionErrors = airavataAPI.getExecutionManager().getExecutionErrors(experimentID, experimentID, null, null, Source.NODE);
				for (ExecutionError experimentExecutionError : experimentExecutionErrors) {
					buffer.append(experimentExecutionError.getErrorMessage());
				}
			}

        }catch(Exception e){
        	e.printStackTrace();
        	throw e;
		} 
    	return buffer.toString();
	}
}
