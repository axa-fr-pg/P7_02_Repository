package swa.poseidon.log;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LogService extends HandlerInterceptorAdapter 
{	
    public static final Logger logger = LogManager.getLogger("poseidon");
    
	@Autowired
	private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
    {	
    	LogService.logger.info( "LogService.preHandle()");
    	// HEADER
    	Enumeration<String> headerNames = request.getHeaderNames();
    	StringBuilder headers = new StringBuilder();
        if (headerNames != null) 
        {
                while (headerNames.hasMoreElements()) 
                {
                	headers.append(request.getHeader(headerNames.nextElement()) + "\n");
                }
        }
    	// BODY
        String indentedBody = null;
        StringBuilder parameters = null;
    	if (request.getContentType() == null)
		{
	    	// GET URLENCODED FORM
	    	Map<String, String[]> parameterList = request.getParameterMap();
	        parameters = new StringBuilder("{");
	        for (String key : parameterList.keySet()) {
	        	parameters.append(key + "=" + parameterList.get(key) + ", ");
	        }
	        if (parameters.length()>2) parameters.delete(parameters.length()-2, parameters.length());
	        parameters.append("}");
		}
		else
		{
	    	// GET JSON
	    	String flatBody="{}";
	    	try {
				InputStream requestInputStream = request.getInputStream();
				byte[] copyOfBody = StreamUtils.copyToByteArray(requestInputStream);
				flatBody = new String(copyOfBody, "UTF-8");
			} 
	    	catch (Exception e) {
				LogService.logger.error( "preHandle() throws Exception", e);
			}
			try {
				JsonNode nodeBody = objectMapper.readTree(flatBody);
		        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
			} 
			catch (JsonProcessingException e) {
		    	LogService.logger.error( "preHandle() throws JsonProcessingException", e);
			}
    	}
		// WRITE LOG
    	LogService.logger.info( "\nREQUEST received\nMETHOD:" + request.getMethod() 
    				+ "\nURL:" + request.getRequestURL() 
    				+ "\nQUERY:" + request.getQueryString()
					+ "\nHEADER:" + headers
					+ "\nFORM:" + parameters
					+ "\nJSON:" + indentedBody);
		return true;
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  
    {
    	LogService.logger.info( "LogService.postHandle()");
     	CacheResponseService cacheResponseService = WebUtils.getNativeResponse(response, CacheResponseService.class);;
    	String flatBody="{}";
    	if (cacheResponseService != null) flatBody=cacheResponseService.toString();  	    	    	
        String indentedBody = null;
		try {
			JsonNode nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} 
		catch (JsonProcessingException e) {
	    	LogService.logger.error( "postHandle() throws JsonProcessingException" );
		}
		LogService.logger.info( "\nRESPONSE sent\nMETHOD:" + request.getMethod() 
				+ "\nURL:" + request.getRequestURL() 
				+ "\nSTATUS:" + response.getStatus()
				+ "\nJSON:" + indentedBody );
    }    
}

