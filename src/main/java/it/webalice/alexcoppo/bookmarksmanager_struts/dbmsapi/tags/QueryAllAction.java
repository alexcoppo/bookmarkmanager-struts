package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.tags;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.TagDAO;
import it.webalice.alexcoppo.bookmarksmanager_struts.util.JsonUtils;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

public class QueryAllAction extends AJAXAction {
    @Override
	protected String processAction(HttpServletRequest request) {
		HibernateResult hr = TagDAO.doQuery(request, "Tag.allByTagAsc");
		
		return JsonUtils.tagListToJson(hr);
    }
}
