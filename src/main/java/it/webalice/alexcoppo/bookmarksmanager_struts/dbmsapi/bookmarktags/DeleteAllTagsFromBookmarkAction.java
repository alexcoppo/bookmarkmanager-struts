package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarktags;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkTagDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;
import net.sf.jautl.text.BasicMarshaller;
import net.sf.jautl.text.StringMarshaller;

public class DeleteAllTagsFromBookmarkAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
    	StringMarshaller sm = new BasicMarshaller();
        long id = sm.asLong(request.getParameter("id"), 0);

        HibernateResult hr = BookmarkTagDAO.deleteAllTagsOfBookmark(request, id);
        
        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK())
        	job.put("id", id);
        else
        	job.put("error", hr.getError());
        
        return job.asString();
	}
}