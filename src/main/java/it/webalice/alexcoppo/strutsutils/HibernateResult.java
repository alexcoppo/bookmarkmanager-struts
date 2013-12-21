package it.webalice.alexcoppo.strutsutils;

public class HibernateResult {
	private Object o;
	private String error;
	public static final String NO_ERROR = "";
	
	public HibernateResult(Object o, String error) {
		this.o = o;
		this.error = error;
	}
	
	public boolean isOK() {
		return error.equals(NO_ERROR);
	}
	
	public String getError() {
		return error;
	}
	
	public Object getObject() {
		return o;
	}
}
