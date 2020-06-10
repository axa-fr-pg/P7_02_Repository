package swa.poseidon.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LogConfiguration implements WebMvcConfigurer 
{	
    @Autowired
    private LogService logService;
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		LogService.logger.debug( "addInterceptors()" );
        registry.addInterceptor(logService);
    }
}
