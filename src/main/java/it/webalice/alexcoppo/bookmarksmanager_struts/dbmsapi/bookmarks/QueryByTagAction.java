package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.bookmarksmanager_struts.util.JsonUtils;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

public class QueryByTagAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
		String tag = request.getParameter("tag");
		
		HibernateResult hr = BookmarkDAO.listByTag(request, tag);
		
		return JsonUtils.bookmarkListToJson(hr);
	}
}
