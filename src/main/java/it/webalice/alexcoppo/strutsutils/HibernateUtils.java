package it.webalice.alexcoppo.strutsutils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtils {
	private static final String SESSION_FACTORY_KEY = HibernateUtils.class.getName();

	public static void setSessionFactory(ServletContext sc, SessionFactory sf) {
		sc.setAttribute(SESSION_FACTORY_KEY, sf);
	}

	public static SessionFactory getSessionFactory(ServletContext sc) {
		return (SessionFactory)sc.getAttribute(SESSION_FACTORY_KEY);
	}
	
	public static Session openSession(HttpServletRequest request) {
		SessionFactory sf = getSessionFactory(request.getServletContext());
		
		return sf.openSession();
	}
}
