package org.cesm.airavata.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class PropertyUtils{
	
	private static Properties props;
	private static Map<String, Integer> nodeHostConfiguration;
	/**
	 * Load a properties file from the classpath
	 * 
	 * @param propsName
	 * @return Properties
	 * @throws Exception
	 */
	public static Properties load(String propsName) throws IOException,Exception {
		if (props == null) {
			props = new Properties();
		}
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertyStream = classLoader.getResourceAsStream(propsName);
		props.load(propertyStream);
		return props;
	}

	/*
	* Load a properties file from the classpath
    * @param propsName
    * @return Properties
    * @throws Exception
    */
   public static Properties loadProperty(String propsName) throws PropertyLoadingException {
       try {
    	if (props == null) {
   			props = new Properties();
   		}
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertyStream = classLoader.getResourceAsStream(propsName);
		   props.load(propertyStream);
		   return props;
	} catch (IOException e) {
		throw new PropertyLoadingException("Error loading file"+ e.getLocalizedMessage(),e);
	}
   }
	/**
	 * Load a Properties File
	 * 
	 * @param propsFile
	 * @return Properties
	 * @throws IOException
	 */
	public static Properties load(File propsFile) throws IOException {
		if (props == null) {
   			props = new Properties();
   		}
    	FileInputStream fis = new FileInputStream(propsFile);
		props.load(fis);
		fis.close();
		return props;
	}
	public static void loadConfigration() throws Exception{
		nodeHostConfiguration = new HashMap<String, Integer>();
		 if(props == null){
			 throw new NullPointerException("Property is null");
		}
		String nodeConfiguration = props.getProperty(ServiceConstants.NODE_CONFIGURATION);
		if(nodeConfiguration != null){
		String [] nodeConf = nodeConfiguration.split(";");	
		for (String node : nodeConf) {
			String[] nodeNameArray = node.split(":");
			if(nodeNameArray != null){
			nodeHostConfiguration.put(nodeNameArray[0].trim(), Integer.parseInt(nodeNameArray[1].trim()));
			}
		}
		}
	}
	public static void setProps(Properties props) {
		PropertyUtils.props = props;
	}

	public static Properties getProps() {
		return props;
	}

	public static void setNodeHostConfiguration(Map<String, Integer> nodeHostConfiguration) {
		PropertyUtils.nodeHostConfiguration = nodeHostConfiguration;
	}

	public static Map<String, Integer> getNodeHostConfiguration()  throws Exception{
		if(nodeHostConfiguration == null){
			loadConfigration();
			}
		return nodeHostConfiguration;
	}
}
