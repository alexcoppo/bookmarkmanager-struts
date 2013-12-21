package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Bookmark;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;

public class FindByUrlAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
		String url = request.getParameter("url");

		HibernateResult hr = BookmarkDAO.findByUrl(request, url);
        
		JSONObjectBuilder job = new JSONObjectBuilder();
		if (hr.isOK()) {
			Bookmark bm = (Bookmark)hr.getObject();
			
			job.put("id", bm.getId());
			if (bm.getId() != 0) {
				job.put("url", bm.getURL());
				job.put("shortUrl", bm.getDomain());
			}
		} else
			job.put("error", hr.getError());
		
        return job.asString();
    }
}
