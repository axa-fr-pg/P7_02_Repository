package swa.poseidon.log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CacheInputStreamService extends ServletInputStream 
{
    private InputStream cachedInputStream;
    
    public CacheInputStreamService(byte[] cache) {
		LogService.logger.debug( "new CachedInputStreamService()" );
        cachedInputStream = new ByteArrayInputStream(cache);
    }

    @Override
    public int read() throws IOException {
		LogService.logger.trace( "read()" );
        return cachedInputStream.read();
    }

    @Override
    public boolean isFinished() {
		LogService.logger.debug( "isFinished()" );
        try {
			return cachedInputStream.available() == 0;
		} catch (IOException e) {
			return true;
		}
    }
    
    @Override
    public boolean isReady() {
		LogService.logger.debug( "isReady()" );
        return true;
    }

	@Override
	public void setReadListener(ReadListener listener) {
		throw new UnsupportedOperationException();
	}	
}
