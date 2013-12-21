package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Bookmark;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;

public class UpdateAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        String url = request.getParameter("url");
        
        HibernateResult hr = BookmarkDAO.update(request, id, url);

        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK()) {
	        job.put("id", ((Bookmark)hr.getObject()).getId()); 
        } else {
	        job.put("error", hr.getError()); 
	        //log error
        }
        
        return job.asString();
	}
}
