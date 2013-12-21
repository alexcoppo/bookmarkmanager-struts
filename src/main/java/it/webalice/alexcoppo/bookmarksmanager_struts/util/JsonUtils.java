package it.webalice.alexcoppo.bookmarksmanager_struts.util;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Bookmark;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.Tag;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import java.util.List;

import net.sf.jautl.json.JSONArrayBuilder;
import net.sf.jautl.json.JSONObjectBuilder;

public class JsonUtils {
	public static String bookmarkListToJson(HibernateResult hr) {
		JSONObjectBuilder job = new JSONObjectBuilder();
		
		if (!hr.isOK()) {
			job.put("error", hr.getError());
		} else {
			JSONArrayBuilder jab = new JSONArrayBuilder();
			
			@SuppressWarnings("unchecked")
			List<Bookmark> bookmarks = (List<Bookmark>)hr.getObject();
			
			for (Bookmark b : bookmarks) {
				job.put("id", b.getId());
				job.put("domain", b.getDomain());
				job.put("url", b.getURL());
				jab.append(job.getObject());
				job.reset();
			}
			
			job.put("items", jab.getArray());
		}
		
		return job.asString();
	}

	public static String tagListToJson(HibernateResult hr) {
		JSONObjectBuilder job = new JSONObjectBuilder();
		
		if (!hr.isOK()) {
			job.put("error", hr.getError());
		} else {
			JSONArrayBuilder jab = new JSONArrayBuilder();
			
			@SuppressWarnings("unchecked")
			List<Tag> tags = (List<Tag>)hr.getObject();
			
			for (Tag t : tags) {
				job.put("id", t.getId());
				job.put("tag", t.getTag());
				jab.append(job.getObject());
				job.reset();
			}
			
			job.put("items", jab.getArray());
		}
		
		return job.asString();
	}
}
