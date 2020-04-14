package com.blesk.accountservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class Thymeleaf {

    @Bean
    public ITemplateResolver templateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        return classLoaderTemplateResolver;
    }

    @Bean
    public TemplateEngine template() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        return templateEngine;
    }
}