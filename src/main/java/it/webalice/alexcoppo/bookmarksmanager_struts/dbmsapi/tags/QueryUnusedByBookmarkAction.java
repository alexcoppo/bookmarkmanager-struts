package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.tags;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.TagDAO;
import it.webalice.alexcoppo.bookmarksmanager_struts.util.JsonUtils;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

import net.sf.jautl.text.BasicMarshaller;
import net.sf.jautl.text.StringMarshaller;

public class QueryUnusedByBookmarkAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
    	StringMarshaller sm = new BasicMarshaller();
    	long id = sm.asLong(request.getParameter("id"), 0);
    	
		HibernateResult hr = TagDAO.doQuery(request, "Tag.listUnusedByBookmark", "bookmarkId", id);
		
		return JsonUtils.tagListToJson(hr);
    }
}
