package it.webalice.alexcoppo.bookmarksmanager_struts.model;

import it.webalice.alexcoppo.strutsutils.HibernateResult;
import it.webalice.alexcoppo.strutsutils.HibernateSessionHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

public class BookmarkDAO {
	public static HibernateResult insert(HttpServletRequest request, String url) {
		Bookmark bm = null;
		
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
	        bm = new Bookmark();
	        bm.setURL(url);
			insert(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(bm, hsh.getError());
	}
	
	public static void insert(Session s, Bookmark bm) {
		s.save(bm);
	}

	public static Bookmark load(Session s, long id) {
		return (Bookmark)s.get(Bookmark.class, id);
	}

	public static HibernateResult load(HttpServletRequest request, long id) {
		Bookmark bm = null;
		
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen())
	        bm = load(hsh.getSession(), id);
		else
			bm = new Bookmark();
		hsh.close();

        return new HibernateResult(bm, hsh.getError());
	}

	@SuppressWarnings("unchecked")
	public static HibernateResult findByUrl(HttpServletRequest request, String url) {
        HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		List<Bookmark> results = null;
		Bookmark bm = null;
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("Bookmark.findByUrl");
			q.setParameter("url", url);
			results = q.list();
			bm = (results.size() == 1) ? results.get(0) : new Bookmark();
		} else
			bm = new Bookmark();
		
		hsh.close();

        return new HibernateResult(bm, hsh.getError());
	}

	public static HibernateResult update(HttpServletRequest request, long id, String newUrl) {
		Bookmark bm = null;
		
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
	        bm = load(hsh.getSession(), id);
	        bm.setURL(newUrl);
			update(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(bm, hsh.getError());
	}

	public static void update(Session s, Bookmark bm) {
		s.update(bm);
	}

	public static HibernateResult delete(HttpServletRequest request, long id) {
        Bookmark bm = null;

        HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		if (hsh.isOpen()) {
	        bm = load(hsh.getSession(), id);
			delete(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(bm, hsh.getError());
	}

	public static void delete(Session s, Bookmark bm) {
		s.delete(bm);
	}

	@SuppressWarnings("unchecked")
	public static HibernateResult listUntagged(HttpServletRequest request) {
        HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		List<Bookmark> results = null;
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("Bookmark.allUntaggedAsc");
			results = q.list();
		} else
			results = new ArrayList<Bookmark>();
		
		hsh.close();

        return new HibernateResult(results, hsh.getError());
	}

	@SuppressWarnings("unchecked")
	public static HibernateResult listByTag(HttpServletRequest request, String tag) {
        HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		List<Bookmark> results = null;
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("Bookmark.allByTagAsc");
			q.setParameter("tag", tag);
			results = q.list();
		} else
			results = new ArrayList<Bookmark>();
		
		hsh.close();

        return new HibernateResult(results, hsh.getError());
	}

	@SuppressWarnings("unchecked")
	public static HibernateResult listByUrlLike(HttpServletRequest request, String urlPattern) {
        HibernateSessionHelper hsh = new HibernateSessionHelper(request);
		List<Bookmark> results = null;
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("Bookmark.allByUrlLikeAsc");
			q.setParameter("urlPattern", urlPattern);
			results = q.list();
		} else
			results = new ArrayList<Bookmark>();
		
		hsh.close();

        return new HibernateResult(results, hsh.getError());
	}
}
