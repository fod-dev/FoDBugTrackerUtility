package com.fortify.processrunner.jira.connection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fortify.processrunner.context.Context;
import com.fortify.processrunner.context.ContextProperty;
import com.fortify.processrunner.context.IContextAware;
import com.fortify.processrunner.context.IContextPropertyProvider;

public class ContextAwareJiraConnectionRetriever 
	extends JiraConnectionRetriever 
	implements IContextAware, IContextPropertyProvider 
{
	public void setContext(Context context) {
		updateConnectionProperties(context);
	}
	
	public List<ContextProperty> getContextProperties(Context context) {
		List<ContextProperty> result = new ArrayList<ContextProperty>();
		result.add(new ContextProperty(IContextJiraConnectionProperties.PRP_BASE_URL, "JIRA base URL", context, getBaseUrl(), true));
		result.add(new ContextProperty(IContextJiraConnectionProperties.PRP_USER_NAME, "JIRA user name", context, StringUtils.isNotBlank(getUserName())?getUserName():"Read from console", false));
		result.add(new ContextProperty(IContextJiraConnectionProperties.PRP_PASSWORD, "JIRA password", context, StringUtils.isNotBlank(getPassword())?"******":"Read from console", false));
		return result;
	}
	
	protected void updateConnectionProperties(Context context) {
		IContextJiraConnectionProperties ctx = context.as(IContextJiraConnectionProperties.class);
		String baseUrl = ctx.getJiraBaseUrl();
		String userName = ctx.getJiraUserName();
		String password = ctx.getJiraPassword();
		
		if ( !StringUtils.isBlank(baseUrl) ) {
			setBaseUrl(baseUrl);
		}
		if ( !StringUtils.isBlank(userName) ) {
			setUserName(userName);
		}
		if ( !StringUtils.isBlank(password) ) {
			setPassword(password);
		}
		
		if ( getUserName()==null ) {
			setUserName(System.console().readLine("JIRA User Name: "));
		}
		if ( getPassword()==null ) {
			setPassword(new String(System.console().readPassword("JIRA Password: ")));
		}
	}
}