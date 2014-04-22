package org.cesm.airavata.client;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.workflow.model.wf.InvalidDataFormatException;
import org.cesm.airavata.message.Message;
import org.cesm.airavata.utils.PropertyLoadingException;
import org.junit.Assert;
import org.junit.Test;

public class WorkflowInvokeTest {
	
	@Test
	public void testRunWorkflow(){
		try {
			WorkflowInvoke invoke = new WorkflowInvoke();
			ArrayList<String> inputs = new ArrayList<String>(Arrays.asList("-e resolution=f19_g16,compset=B_2000,machine=carter,casename=test_airavata1,username=pzheng -v"));
			Message message = new Message();
			message.setWorkflowName("Configure_case");
			message.setServiceName("Configure_case_invoke");
			message.setInputs(inputs);
			String expid = invoke.runWorkflow(message);
			
			Assert.assertNotNull(expid);
		} catch (InvalidDataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AiravataAPIInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyLoadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
  
}
