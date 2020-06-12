package swa.poseidon.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CacheResponseService extends HttpServletResponseWrapper 
{
    private ByteArrayOutputStream stream;
    private String charSet;

    public CacheResponseService(HttpServletResponse response) 
    {
        super(response);
		LogService.logger.debug( "new CacheResponseService()" );
		stream = new ByteArrayOutputStream();
		charSet = response.getCharacterEncoding();
    }

    @Override
    public String toString() {
		LogService.logger.debug( "CacheResponseService.toString()" );
        try {
			return stream.toString(charSet);
		} catch (UnsupportedEncodingException e) {
			LogService.logger.error( "toString() throws UnsupportedEncodingException" );
			return "";
		}
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
		LogService.logger.debug( "CacheResponseService.getOutputStream()" );
        return new CacheOutputStreamService(super.getOutputStream(), stream);
    }
}
