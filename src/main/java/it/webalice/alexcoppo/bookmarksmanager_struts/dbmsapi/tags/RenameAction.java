package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.tags;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.Tag;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.TagDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;
import net.sf.jautl.text.BasicMarshaller;
import net.sf.jautl.text.StringMarshaller;

public class RenameAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
    	StringMarshaller sm = new BasicMarshaller();
        long id = sm.asLong(request.getParameter("id"), 0);
        String newTag = request.getParameter("tag");
        
        HibernateResult hr = TagDAO.update(request, id, newTag);

        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK()) {
	        job.put("id", ((Tag)hr.getObject()).getId()); 
        } else {
	        job.put("error", hr.getError()); 
	        //log error
        }
        
        return job.asString();
    }
}
