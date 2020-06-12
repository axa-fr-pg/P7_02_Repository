package swa.poseidon.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class CacheOutputStreamService extends ServletOutputStream 
{
    private ServletOutputStream stream;
    private ByteArrayOutputStream cache;
    
    public CacheOutputStreamService(ServletOutputStream stream, ByteArrayOutputStream cache) {
		LogService.logger.debug( "new CacheOutputStreamService()" );
        this.stream = stream;
        this.cache = cache;
    }

    @Override
    public boolean isReady() {
		LogService.logger.debug( "CacheOutputStreamService.isReady()" );
        return true;
    }

	@Override
	public void setWriteListener(WriteListener listener) {
		LogService.logger.debug( "CacheOutputStreamService.setWriteListener()" );
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(int b) throws IOException 
	{
        stream.write(b);
        cache.write(b);
	}	
}
