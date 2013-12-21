package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.tags;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.json.JSONObjectBuilder;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.Tag;
import it.webalice.alexcoppo.bookmarksmanager_struts.model.TagDAO;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

public class FindAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
        String tag = request.getParameter("tag");
        
        HibernateResult hr = TagDAO.doQuery(request, "Tag.findByTag", "tag", tag);

        JSONObjectBuilder job = new JSONObjectBuilder();
        if (hr.isOK()) {
        	@SuppressWarnings("unchecked")
			List<Tag> tags = (List<Tag>)hr.getObject();
        	if (tags.size() == 0)
        		job.put("id", 0);
        	else
        		job.put("id", tags.get(0).getId());
        } else {
	        job.put("error", hr.getError()); 
	        //log error
        }

        return job.asString();
    }
}
