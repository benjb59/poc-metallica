package fr.insee.metallica.pocprotools.command.processor.payload;

import java.util.Map;

public class HttpPayload {
	public static enum HttpMethod {
		POST, GET, DELETE, PUT
	}
	
	private Map<String, String> headers;
	private HttpMethod method;
	private Object body;
	String url;

	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
