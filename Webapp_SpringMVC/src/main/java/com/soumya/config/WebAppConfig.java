package com.soumya.config;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration.Dynamic;

public class WebAppConfig  implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        
        context.register(Configuration1.class);
        
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        
        Dynamic customDispatcher = servletContext.addServlet("mydis", dispatcherServlet);
        
        customDispatcher.setLoadOnStartup(1);
        
        customDispatcher.addMapping("/");
				
	}

}
