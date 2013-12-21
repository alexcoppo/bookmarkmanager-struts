package it.webalice.alexcoppo.bookmarksmanager_struts.model;

import it.webalice.alexcoppo.strutsutils.HibernateResult;
import it.webalice.alexcoppo.strutsutils.HibernateSessionHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

public class BookmarkTagDAO {
	public static void save(Session s, BookmarkTag bt) {
		s.save(bt);
	}
	
	public static BookmarkTag load(Session s, long id) {
		return (BookmarkTag)s.get(BookmarkTag.class, id);
	}

	public static void update(Session s, BookmarkTag bt) {
		s.update(bt);
	}

	public static void delete(Session s, BookmarkTag bt) {
		s.delete(bt);
	}
	
	@SuppressWarnings("unchecked")
	public static HibernateResult getAllTagsUsedByBookmark(HttpServletRequest request, long id) {
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);

		List<Tag> tags = new ArrayList<Tag>();
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("BookmarkTag.allBookmarkTagsUsedByBookmarkAsc");
			q.setParameter("id", id);
			tags = q.list(); 
		} else
			tags = new ArrayList<Tag>();
		
		hsh.close();

        return new HibernateResult(tags, hsh.getError());
	}
	
	@SuppressWarnings("unchecked")
	public static HibernateResult getAllTagsUnusedByBookmark(HttpServletRequest request, long id) {
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);

		List<Tag> tags = new ArrayList<Tag>();
		
		if (hsh.isOpen()) {
			Query q = hsh.getSession().getNamedQuery("BookmarkTag.allBookmarkTagsUnusedByBookmarkAsc");
			q.setParameter("id", id);
			tags = q.list(); 
		} else
			tags = new ArrayList<Tag>();
		
		hsh.close();

        return new HibernateResult(tags, hsh.getError());
	}

	public static HibernateResult addTagToBookmark(HttpServletRequest request, long id, long tid) {
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);

		if (hsh.isOpen()) {
			Bookmark bm = BookmarkDAO.load(hsh.getSession(), id);
			Tag t = TagDAO.load(hsh.getSession(), tid);
			bm.getTags().add(t);
			BookmarkDAO.update(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(null, hsh.getError());
	}
	
	public static HibernateResult deleteTagOfBookmark(HttpServletRequest request, long id, long tid) {
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);

		if (hsh.isOpen()) {
			Bookmark bm = BookmarkDAO.load(hsh.getSession(), id);
			Tag t = TagDAO.load(hsh.getSession(), tid);
			bm.getTags().remove(t);
			BookmarkDAO.update(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(null, hsh.getError());
	}
	
	public static HibernateResult deleteAllTagsOfBookmark(HttpServletRequest request, long id) {
		HibernateSessionHelper hsh = new HibernateSessionHelper(request);

		if (hsh.isOpen()) {
			Bookmark bm = BookmarkDAO.load(hsh.getSession(), id);
			bm.setTags(new HashSet<Tag>());
			BookmarkDAO.update(hsh.getSession(), bm);
		}
		hsh.close();

        return new HibernateResult(null, hsh.getError());
	}
}
