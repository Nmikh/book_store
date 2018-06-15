package com.configs;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class MvcConfig extends WebMvcConfigurerAdapter {

  private static final String[] CLASSPATH_RESOURCE_LOCATION = {
      "classpath:/static/js/",
      "classpath:/static/",
      "classpath:/static/css",
      "classpath:/static/npm/node_modules/bootstrap/dist/css",
      "classpath:/static/npm/node_modules/jquery/dist/"
  };

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!registry.hasMappingForPattern("/static/**")) {
      registry.addResourceHandler("/static/**")
          .addResourceLocations((CLASSPATH_RESOURCE_LOCATION));
    }
  }
}
