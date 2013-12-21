package it.webalice.alexcoppo.bookmarksmanager_struts.dbmsapi.bookmarks;

import javax.servlet.http.HttpServletRequest;

import it.webalice.alexcoppo.bookmarksmanager_struts.model.BookmarkDAO;
import it.webalice.alexcoppo.bookmarksmanager_struts.util.JsonUtils;
import it.webalice.alexcoppo.strutsutils.AJAXAction;
import it.webalice.alexcoppo.strutsutils.HibernateResult;

public class QueryUntaggedAction extends AJAXAction {
	@Override
	protected String processAction(HttpServletRequest request) {
		HibernateResult hr = BookmarkDAO.listUntagged(request);

		return JsonUtils.bookmarkListToJson(hr);
	}
}
