package br.com.jeanfbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {


    @Value(("${spring.thymeleaf.prefix}"))
    private String prefix;

    @Value("${spring.thymeleaf.suffix}")
    private String sufix;

    @Value("${spring.thymeleaf.mode}")
    private String mode;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ITemplateResolver templateResolver(){

        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix(prefix);
        resolver.setSuffix(sufix);
        resolver.setCacheable(false);
        resolver.setTemplateMode(mode);
        resolver.setOrder(0);

        return resolver;
    }
}
