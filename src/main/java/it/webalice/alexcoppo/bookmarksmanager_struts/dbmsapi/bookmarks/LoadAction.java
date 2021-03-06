package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Bookmark;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;
import net.sf.jautl.text.BasicMarshaller;
import net.sf.jautl.text.StringMarshaller;

public class LoadAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
		StringMarshaller sm = new BasicMarshaller();
		long id = sm.asLong(request.getParameter("id"), 0);

		HibernateResult hr = BookmarkDAO.load(request, id);
        
		JSONObjectBuilder job = new JSONObjectBuilder();
		if (hr.isOK()) {
			Bookmark bm = (Bookmark)hr.getObject();
			
			job.put("id", bm.getId());
			job.put("url", bm.getURL());
			job.put("shortUrl", bm.getDomain());
		} else
			job.put("error", hr.getError());
		
        return job.asString();
    }
}
