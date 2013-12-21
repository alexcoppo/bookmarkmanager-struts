package it.webalice.alexcoppo.strutsutils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AJAXAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String result = processAction(request);
	    out.println(result);
	    out.flush();
	    
	    return null;
	}

	protected abstract String processAction(HttpServletRequest request);
}