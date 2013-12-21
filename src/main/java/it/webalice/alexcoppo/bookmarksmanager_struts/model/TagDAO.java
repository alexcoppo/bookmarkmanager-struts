package it.webalice.alexcoppo.bookmarksmanager_struts.model;

import it.webalice.alexcoppo.strutsutils.HibernateResult;
import it.webalice.alexcoppo.strutsutils.HibernateSessionHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

public class TagDAO {
	public static HibernateResult insert(HttpServletRequest request, String tag) {
        Tag t = new Tag();
        
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
	        t.setTag(tag);
	        insert(hsh.getSession(), t);
		}
		hsh.close();
		
        return new HibernateResult(t, hsh.getError());
	}

	public static void insert(Session s, Tag t) {
		s.save(t);
	}
	
	public static Tag load(Session s, long id) {
		return (Tag)s.get(Tag.class, id);
	}

	public static HibernateResult update(HttpServletRequest request, long id, String newTag) {
		Tag t = null;

		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
			t = load(hsh.getSession(), id);

			if (t == null) {
				hsh.close();
		        return new HibernateResult(null, "Tag not found");
			} 

			t.setTag(newTag);
			update(hsh.getSession(), t);
		}
		hsh.close();
        
        return new HibernateResult(t, hsh.getError());
	}

	public static void update(Session s, Tag t) {
		s.update(t);
	}

	public static HibernateResult delete(HttpServletRequest request, long id) {
		Tag tag = null;

		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
			tag = load(hsh.getSession(), id);

			if (tag == null) {
				hsh.close();
		        return new HibernateResult(null, "Tag not found");
			} 

			delete(hsh.getSession(), tag);
        }
		hsh.close();

        return new HibernateResult(tag, hsh.getError());
	}

	public static void delete(Session s, Tag t) {
		s.delete(t);
	}
	
	@SuppressWarnings("unchecked")
	public static HibernateResult doQuery(HttpServletRequest request, String queryName) {
		List<Tag> results = null;
		
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		
		if (hsh.isOpen()) {
            Query q = hsh.getSession().getNamedQuery("Tag.allByTagAsc");
            results = q.list();
        }
		hsh.close();

        return new HibernateResult(results, hsh.getError());
	}

	@SuppressWarnings("unchecked")
	public static HibernateResult doQuery(HttpServletRequest request, String queryName, String paramName, Object paramValue) {
		List<Tag> result;
		
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		
		if (hsh.isOpen()) {
            Query q = hsh.getSession().getNamedQuery(queryName);
            q.setParameter(paramName, paramValue);
            result = q.list();
        } else
            result = new ArrayList<Tag>();
        	
		hsh.close();

		return new HibernateResult(result, hsh.getError());
	}
}
