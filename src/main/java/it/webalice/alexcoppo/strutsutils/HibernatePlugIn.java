package it.webalice.alexcoppo.strutsutils;

import java.net.URL;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernatePlugIn implements PlugIn {
	private String path = "/hibernate.cfg.xml";
	private SessionFactory sf;
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		try {
			URL url = HibernatePlugIn.class.getResource(path);
			
			Configuration hc = new Configuration().configure(url);
			
			ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(hc.getProperties()).buildServiceRegistry();
			
			sf = hc.buildSessionFactory(sr);

			HibernateUtils.setSessionFactory(servlet.getServletContext(), sf);
		} catch (HibernateException he) {
			throw new ServletException(he);
		}
    }
	
	@Override
	public void destroy() {
      try {
    	  sf.close();
       } catch (HibernateException e) {
          e.printStackTrace();
       }
	}
}
