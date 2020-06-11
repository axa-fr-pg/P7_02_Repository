package swa.poseidon.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LogConfiguration implements WebMvcConfigurer 
{	
	// add path pattern ET exclude path pattern
    @Autowired
    private LogService logService;

	@Value("${poseidon.environment.production}")
	private boolean isProductionEnvironment;

    @Override
    public void addInterceptors(InterceptorRegistry registry) 
    {
		LogService.logger.debug( "addInterceptors()" );
		if (isProductionEnvironment) registry.addInterceptor(logService);
    }
}
