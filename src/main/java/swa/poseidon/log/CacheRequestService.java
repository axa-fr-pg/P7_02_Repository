package swa.poseidon.log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.util.StreamUtils;

public class CacheRequestService extends SecurityContextHolderAwareRequestWrapper 
{
    private byte[] cache;
    private String charSet;
    Map<String, String[]> parameterMap; 

    public CacheRequestService(HttpServletRequest request) throws IOException 
    {
        super(request,"");
		LogService.logger.info( "new CacheRequestService()" );
        InputStream requestInputStream = request.getInputStream();
        cache = StreamUtils.copyToByteArray(requestInputStream);
		charSet = request.getCharacterEncoding();
		parameterMap = new HashMap<String, String[]>();
		parameterMap.putAll(request.getParameterMap());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
		LogService.logger.info( "CacheRequestService.getInputStream()" );
        return new CacheInputStreamService(cache);
    }
    
    @Override
    public String toString() {
		LogService.logger.info( "CacheRequestService.toString()" );
        try {
            return new String(cache, charSet);
		} catch (UnsupportedEncodingException e) {
			LogService.logger.error( "toString() throws UnsupportedEncodingException" );
			return "";
		}
    }
    
    @Override
    public Map<String, String[]> getParameterMap()
    {
    		return parameterMap;
    }
/*
    @Override
    public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException();
    }*/
}
