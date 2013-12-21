package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Bookmark;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;
import net.sf.jautl.text.BasicMarshaller;
import net.sf.jautl.text.StringMarshaller;

public class DeleteAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
    	StringMarshaller sm = new BasicMarshaller();
        long id = sm.asLong(request.getParameter("id"), 0);
        
        HibernateResult hr = BookmarkDAO.delete(request, id);
        
        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK())
	        job.put("id", ((Bookmark)hr.getObject()).getId());
        else {
	        job.put("error", hr.getError()); 
        	//log error
        }

        return job.asString();
    }
}
