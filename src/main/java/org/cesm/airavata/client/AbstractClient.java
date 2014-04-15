package org.cesm.airavata.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.airavata.client.AiravataAPIFactory;
import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.registry.api.PasswordCallback;
import org.cesm.airavata.utils.PasswordCallbackImpl;
import org.cesm.airavata.utils.PropertyLoadingException;
import org.cesm.airavata.utils.PropertyUtils;
import org.cesm.airavata.utils.ServiceConstants;

public abstract class AbstractClient {
	private static Properties properties;
	private static AiravataAPI airavataAPI;
	public static Properties getProperties() throws PropertyLoadingException {
		if(null == properties){
			properties = PropertyUtils.loadProperty(ServiceConstants.PROPERTYFILE_NAME);
		}
		return properties;
	}
	/**
	 * Create Airavata API function
	 * @return
	 * @throws PropertyLoadingException
	 * @throws AiravataAPIInvocationException
	 * @throws URISyntaxException
	 */
	protected AiravataAPI getAiravataAPI() throws PropertyLoadingException, AiravataAPIInvocationException, URISyntaxException {
		if(airavataAPI == null){
		PasswordCallback passwordCallback = new PasswordCallbackImpl();
		String username = getProperties().getProperty(ServiceConstants.AIRAVATA_USERNAME,"admin");
		String registryURL = getProperties().getProperty(ServiceConstants.AIRAVATA_REGISTRYURL,null);
		String gateway = getProperties().getProperty(ServiceConstants.AIRAVATA_GATEWAYNAME,"default");
		airavataAPI = AiravataAPIFactory.getAPI(new URI(registryURL), gateway, username,
				passwordCallback);
		}
		return airavataAPI;
	}

}
