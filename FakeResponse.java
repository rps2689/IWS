package edu.upenn.cis455.hw1;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class FakeResponse implements HttpServletResponse {

	public FakeResponse(){
		InitCharEnc();
		this.cookieList = new ArrayList<Cookie>();
		this.responseHeaders = new HashMap<String, String>();
	}

	public void addCookie(Cookie arg0) {
		// If cookie already present, replace it with the latest one
		for(int i=0; i<this.cookieList.size(); i++){
			if(this.cookieList.get(i).getName().equalsIgnoreCase(arg0.getName())){
				cookieList.remove(i);
			}
		}
		cookieList.add(arg0);
	}

	// This method is called by the servletWrapper/PrintWriterCreationMethod 
	// to print the header string for cookie in the response
	public String makeCookieHeader(){
		String out = "Set-Cookie: ";
		for(int i=0; i<cookieList.size()-1; i++){
			out += cookieList.get(i).getName()+"="+cookieList.get(i).getValue()+" ;";
		}
		out += cookieList.get(cookieList.size()-1).getName()+"="+cookieList.get(cookieList.size()-1).getValue();
		return out.trim();
	}

	public boolean containsHeader(String arg0) {
		return this.responseHeaders.containsKey(arg0);
	}

	// TO DO (need more info on session)
	public String encodeURL(String arg0) {
		return arg0;
	}

	// TO DO (need more info on session)
	public String encodeRedirectURL(String arg0) {
		return arg0;
	}

	// Deprecated
	public String encodeUrl(String arg0) {
		return null;
	}

	// Deprecated
	public String encodeRedirectUrl(String arg0) {
		return null;
	}

	public void sendError(int arg0, String arg1) throws IOException {

	}

	// TO DO
	public void sendError(int arg0) throws IOException {

	}

	// TO DO
	public void sendRedirect(String arg0) throws IOException {
		System.out.println("[DEBUG] redirect to " + arg0 + " requested");
		System.out.println("[DEBUG] stack trace: ");
		Exception e = new Exception();
		StackTraceElement[] frames = e.getStackTrace();
		for (int i = 0; i < frames.length; i++) {
			System.out.print("[DEBUG]   ");
			System.out.println(frames[i].toString());
		}
	}

	// Assumption: Difference between this and addHeader is only that here we replace value if 
	// header already exists, and in the case of addHeader we have multiple values for same header
	// Also assuming that I don't have to specifically "remove" elements in case value already exists for
	// that header
	public void setDateHeader(String arg0, long arg1) {
		this.responseHeaders.put(arg0, String.valueOf(arg1));
	}

	// Assumption: for multiple date values corresponding to the same date, there is a blank space between 
	// consecutive dates
	public void addDateHeader(String arg0, long arg1) {
		if(this.responseHeaders.containsKey(arg0)){
			this.responseHeaders.put(arg0, this.responseHeaders.get(arg0)+" "+String.valueOf(arg1));
		}
		else{
			this.responseHeaders.put(arg0, String.valueOf(arg1));
		}
	}

	// Assumption: Difference between this and addHeader is only that here we replace value if 
	// header already exists, and in the case of addHeader we have multiple values for same header
	// Also assuming that I don't have to specifically "remove" elements in case value already exists for
	// that header
	public void setHeader(String arg0, String arg1) {
		this.responseHeaders.put(arg0, arg1);
	}

	// Assumption: for multiple date values corresponding to the same date, there is a blank space between 
	// consecutive dates
	public void addHeader(String arg0, String arg1) {
		if(this.responseHeaders.containsKey(arg0)){
			this.responseHeaders.put(arg0, this.responseHeaders.get(arg0)+" "+arg1);
		}
		else{
			this.responseHeaders.put(arg0, arg1);
		}
	}

	// Assumption: Difference between this and addHeader is only that here we replace value if 
	// header already exists, and in the case of addHeader we have multiple values for same header
	// Also assuming that I don't have to specifically "remove" elements in case value already exists for
	// that header
	public void setIntHeader(String arg0, int arg1) {
		this.responseHeaders.put(arg0, String.valueOf(arg1));
	}

	// Assumption: for multiple date values corresponding to the same date, there is a blank space between 
	// consecutive dates
	public void addIntHeader(String arg0, int arg1) {
		if(this.responseHeaders.containsKey(arg0)){
			this.responseHeaders.put(arg0, this.responseHeaders.get(arg0)+" "+String.valueOf(arg1));
		}
		else{
			this.responseHeaders.put(arg0, String.valueOf(arg1));
		}
	}

	public void setStatus(int arg0) {
		this.currentStatus = arg0;
	}

	// Deprecated
	public void setStatus(int arg0, String arg1) {
		
	}

	public String getCharacterEncoding() {
		return "ISO-8859-1";
	}
		
	public String getContentType() {
		return this.m_contentType==null?"text/html":this.m_contentType;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	// TO DO
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(System.out, true);
	}

	public void setCharacterEncoding(String arg0) {
		if(charEnc.contains(arg0))
			this.curCharEnc = arg0;
	}

	private void InitCharEnc(){
		charEnc.add("ISO-8859-1");
		charEnc.add("US-ASCII");
		charEnc.add("UTF-8");
		charEnc.add("UTF-16");
	}

	public void setContentLength(int arg0) {
		this.m_contentLength = arg0;
	}

	public void setContentType(String arg0) {
		this.m_contentType = arg0; 
	}

	public void setBufferSize(int arg0) {
		this.m_bufferSize = arg0;
	}

	public int getBufferSize() {
		return this.m_bufferSize;
	}

	// TO DO
	public void flushBuffer() throws IOException {
		
	}

	// TO DO
	public void resetBuffer() {
		
	}

	// TO DO
	public boolean isCommitted() {
		return false;
	}

	// TO DO
	public void reset() {
		
	}

	// Figure out the locales possible and if arg0 is one of them then set it to arg0
	public void setLocale(Locale arg0) {
		
	}

	// TO DO
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	private String m_contentType = null;
	private int m_contentLength = 0;
	private int m_bufferSize = 0;
	
	private StringWriter buf;
	private ArrayList<Cookie> cookieList;
	private int currentStatus;
	private String curCharEnc = null;
	
	// HashMap to hold headers and a map to their values
	HashMap<String, String> responseHeaders;

	// This is an arraylist that holds the character encodings that are legal
	private static ArrayList<String>  charEnc = new ArrayList<String>();
}
