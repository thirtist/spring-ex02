package org.zerock.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BoardListener
 *
 */
//@WebListener
public class BoardListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BoardListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext app = sce.getServletContext();
         
         String contextRoot = app.getContextPath();
         String staticPath
         = "https://objectstorage.ap-seoul-1.oraclecloud.com/n/cnnwglbw5agh/b/bucket-20210216-1416/o/";
         
         app.setAttribute("root", contextRoot);
         app.setAttribute("staticPath", staticPath);
         
    }
	
}
