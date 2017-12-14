package org.myretail.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class PropertiesUtil {

	@Autowired
    private Environment environment;

	public String getRedskyURL() {
        return environment.getProperty("redsky.url");       
    }
	
	public String loginUser() {
        return environment.getProperty("login.user");       
    }
	
	public String getLoginPassword() {
        return environment.getProperty("login.pswd");       
    }
	
	
	
}
