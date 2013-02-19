package edu.upenn.cis455.hw1;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author Todd J. Green
 */
class FakeSession implements HttpSession {

	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	// TO DO
	public ServletContext getServletContext() {
		
		return null;
	}

	public void setMaxInactiveInterval(int arg0) {
		this.max_time_int = arg0;
	}

	public int getMaxInactiveInterval() {
		return this.max_time_int>=0?this.max_time_int:-1;
	}

	// Deprecated
	public HttpSessionContext getSessionContext() {
		return null;
	}

	public Object getAttribute(String arg0) {
		return m_props.get(arg0);
	}

	public Object getValue(String arg0) {
		return m_props.get(arg0);
	}

	public Enumeration getAttributeNames() {
		return m_props.keys();
	}

	// Deprecated
	public String[] getValueNames() {
		return null;
	}

	public void setAttribute(String arg0, Object arg1) {
		m_props.put(arg0, arg1);
	}

	public void putValue(String arg0, Object arg1) {
		m_props.put(arg0, arg1);
	}

	public void removeAttribute(String arg0) {
		m_props.remove(arg0);
	}

	public void removeValue(String arg0) {
		m_props.remove(arg0);
	}

	public void invalidate() {
		m_valid = false;
	}

	// TO DO
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	boolean isValid() {
		return m_valid;
	}

	private Properties m_props = new Properties();
	private boolean m_valid = true;
	private int max_time_int = 0;
}
