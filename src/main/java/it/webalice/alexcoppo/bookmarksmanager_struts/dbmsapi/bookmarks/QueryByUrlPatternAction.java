package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.bookmarksmanager_struts.util.JsonUtils;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

import javax.servlet.http.HttpServletRequest;

public class QueryByUrlPatternAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
		String urlPattern = request.getParameter("pattern");
		HibernateResult hr = BookmarkDAO.listByUrlLike(request, urlPattern);
		
		return JsonUtils.bookmarkListToJson(hr);
	}
}