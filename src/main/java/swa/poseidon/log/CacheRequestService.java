package swa.poseidon.log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

public class CacheRequestService extends HttpServletRequestWrapper 
{
    private byte[] streamContentCache;
    private MultiValueMap<String, String> parameterMap; 

    public CacheRequestService(HttpServletRequest request) throws IOException 
    {
        super(request);
		LogService.logger.info( "new CacheRequestService()" );
        InputStream requestInputStream = request.getInputStream();
        streamContentCache = StreamUtils.copyToByteArray(requestInputStream);
        LogService.logger.info("CACHE CONTENT: " + toString());
		parameterMap = getParameterMapFromBody();
    }
    
    private MultiValueMap<String, String> getParameterMapFromBody()
    {
        FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();
    	HttpInputMessage inputMessage = new ServletServerHttpRequest(this)
    	{
            @Override
            public InputStream getBody() throws IOException 
            {
            	CacheRequestService request = (CacheRequestService) getServletRequest();
                return request.getInputStream();
            }
    	};
        try {
			return formConverter.read(null, inputMessage);
		} catch (HttpMessageNotReadableException | IOException e) {
			return null;
		}
    }
  
    @Override
    public ServletInputStream getInputStream() throws IOException {
		LogService.logger.info( "CacheRequestService.getInputStream()" );
        return new CacheInputStreamService(streamContentCache);
    }
    
    @Override
    public String toString() 
    {
		LogService.logger.info( "CacheRequestService.toString()" );
        try {
            return new String(streamContentCache, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LogService.logger.error( "toString() throws UnsupportedEncodingException" );
			return "";
		}
    }
 
    @Override
    public Map<String, String[]> getParameterMap()
    {
        Map<String, String[]> result = new LinkedHashMap<>();
        Enumeration<String> names = getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            result.put(name, this.getParameterValues(name));
        }
        return result;
    }
    
    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> names = new LinkedHashSet<>();
        names.addAll(Collections.list(super.getParameterNames()));
        names.addAll(parameterMap.keySet());
        return Collections.enumeration(names);
    }
    
    @Override
    public String getParameter(String name) {
        String queryStringValue = super.getParameter(name);
        String formValue = parameterMap.getFirst(name);
        if (queryStringValue == null) return formValue;
        return queryStringValue;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] queryStringValues = super.getParameterValues(name);
        List<String> formValues = parameterMap.get(name);
        if (formValues == null) return queryStringValues;
        if (queryStringValues == null) return formValues.toArray(new String[formValues.size()]);
        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(queryStringValues));
        result.addAll(formValues);
        return result.toArray(new String[result.size()]);        
    }
}
