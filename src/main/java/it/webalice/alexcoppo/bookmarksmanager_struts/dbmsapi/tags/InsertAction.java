package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.tags;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.Tag;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.TagDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;

public class InsertAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
        String tag = request.getParameter("tag");
        
		HibernateResult hr = TagDAO.insert(request, tag);
        
        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK())
	        job.put("id", ((Tag)hr.getObject()).getId());
        else {
	        job.put("error", hr.getError()); 
        	//log error
        }
        return job.asString();
    }
}
