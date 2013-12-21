package it.webalice.alexcoppo.strutsutils;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

public class HibernateSessionHelper {
	private Session session = null;
	private String error = "";
	
	public HibernateSessionHelper(HttpServletRequest request) {
		session = null;
        error = "";

        try {
        	session = HibernateUtils.openSession(request);
        	session.beginTransaction();
        } catch (RuntimeException re) {
        	error = re.getMessage();
        	cleanup();
        }
	}
	
	public void close() {
		try {
			if (isOpen()) session.getTransaction().commit();
		} catch (RuntimeException re) {
        	error = re.getMessage();
	    }
		cleanup();
	}
	
	public boolean isOpen() {
		return session != null;
	}
	
	public boolean isOK() {
		return error.equals("");
	}
	
	public Session getSession() {
		return session;
	}
	
	public String getError() {
		return error;
	}
	
	private void cleanup() {
        try {
	        if (isOpen()) session.close();
	        session = null;
        } catch (Exception e) {
        }
	}
}
