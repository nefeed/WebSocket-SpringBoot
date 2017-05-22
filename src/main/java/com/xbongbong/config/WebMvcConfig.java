package com.xbongbong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-07
 * Time: 16:56
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    protected static final String TAG = "WebMvcConfig";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        // urlPath 是希望 用户 访问的地址栏显示，ViewName 是 html 的目录+文件名
        registry.addViewController("/chat").setViewName("websocket");
    }

    // Spring Boot 管理平台，暂时关闭
//    @Bean
//    public CustomerInterceptor customerInterceptor() {
//        return new CustomerInterceptor();
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
//        registry.addInterceptor(customerInterceptor());
//    }

    // ------------------------------- Thymeleaf 的配置， Spring Boot 不需要，已经自动配置 Begin -------------------------------
//    @Bean
//    public TemplateResolver templateResolver() {
//        TemplateResolver templateResolver =
//                new ServletContextTemplateResolver();
//        templateResolver.setPrefix("/WEB-INF/templates");
//        templateResolver.setSuffix(".html");
////        templateResolver.setContentType("text/html;charset=UTF-8");
//        templateResolver.setTemplateMode("HTML5");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//        thymeleafViewResolver.setTemplateEngine(templateEngine());
//        thymeleafViewResolver.setViewClass(ThymeleafView.class);
//        return thymeleafViewResolver;
//    }
    // ------------------------------- Thymeleaf 的配置， Spring Boot 不需要，已经自动配置 End -------------------------------

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/**");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false); // 不忽略 URL 请求中的"."
    }
}
