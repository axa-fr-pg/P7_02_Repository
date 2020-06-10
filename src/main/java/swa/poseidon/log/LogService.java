package swa.poseidon.log;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    	LogService.logger.info( "preHandle()");
    	String flatBody="{}";
    	try {
			InputStream requestInputStream = request.getInputStream();
			byte[] copyOfBody = StreamUtils.copyToByteArray(requestInputStream);
			String charSet = request.getCharacterEncoding();
			flatBody = new String(copyOfBody, charSet);
		} 
    	catch (IOException e) {
			LogService.logger.error( "afterCompletion() throws IOException", e);
		}
        String indentedBody = "Cannot display body due to JsonProcessingException";
		try {
			JsonNode nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} 
		catch (JsonProcessingException e) {
	    	LogService.logger.error( "preHandle() throws JsonProcessingException", e);
		}
    	LogService.logger.info( "Request method:" + request.getMethod() + " URL:"
					+ request.getRequestURL() + " query:" + request.getQueryString()
					+ " body:\n" + indentedBody);
		return true;
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  
    {
    	// ContentCachingResponseWrapper
    	LogService.logger.info( "postHandle()");
     	CacheResponseService cacheResponseService = WebUtils.getNativeResponse(response, CacheResponseService.class);;
    	String flatBody=cacheResponseService.toString();
    	    	
    	
        String indentedBody = "Cannot display body due to JsonProcessingException";
		try {
			JsonNode nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} 
		catch (JsonProcessingException e) {
	    	LogService.logger.error( "afterCompletion() throws JsonProcessingException" );
		}
		LogService.logger.info( "Response method:" + request.getMethod() + " URL:"
				+ request.getRequestURL() + " status:" + response.getStatus()
				+ " body:\n" + indentedBody );
    }
    
    /*
    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
    {
    	LogService.logger.info( "afterCompletion()");
    	String flatBody="{}";
    	    	
    	*****
    	try {
    		ByteArrayOutputStream responseOutputStream = response.getOutputStream().; 
			byte[] copyOfBody = StreamUtils.copyToByteArray(responseOutputStream);
			String charSet = request.getCharacterEncoding();
			flatBody = new String(copyOfBody, charSet);
		} 
    	catch (IOException e) {
			LogService.logger.error( "afterCompletion() throws IOException", e);
		}
		*****
    	
    	CacheResponseService cacheResponseService = (CacheResponseService) response;
    	String indentedBody = cacheResponseService.toString();
    	
   //     String indentedBody = "Cannot display body due to JsonProcessingException";
		try {
			JsonNode nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} 
		catch (JsonProcessingException e) {
	    	LogService.logger.error( "afterCompletion() throws JsonProcessingException" );
		}
		LogService.logger.info( "Response method:" + request.getMethod() + " URL:"
				+ request.getRequestURL() + " status:" + response.getStatus()
				+ " body:\n" + indentedBody );
    }*/
}

