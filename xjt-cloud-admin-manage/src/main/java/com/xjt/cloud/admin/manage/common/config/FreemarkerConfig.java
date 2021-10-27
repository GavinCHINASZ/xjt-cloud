package com.xjt.cloud.admin.manage.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/26 14:46
 * @Description:
 */
@Configuration
public class FreemarkerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/").setViewName("index");//默认访问页面
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE);//最先执行过滤
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index", "/login","/error","/resource/**","/WEB-INF/static/**");//不拦截的路径
    }*/

   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
       // 为当前的资源处理器设置一个优先级
       registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/static/");
    }

    /**
     * 消息内容转换配置
     * 配置fastJson返回json转换
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,
                SerializerFeature.QuoteFieldNames
        );
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.TEXT_HTML);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);

    }

    /**
     * 配置请求视图映射
     * @return
     */
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setContentType("text/html;charset=UTF-8");
        return internalResourceViewResolver;
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //先获取到converter列表
        List<HttpMessageConverter<?>> converters = builder.build().getMessageConverters();
        for(HttpMessageConverter<?> converter : converters){
            //因为我们只想要jsonConverter支持对text/html的解析
            if(converter instanceof MappingJackson2HttpMessageConverter){
                try{
                    //先将原先支持的MediaType列表拷出
                    List<MediaType> mediaTypeList = new ArrayList<>(converter.getSupportedMediaTypes());
                    //加入对text/html的支持
                    mediaTypeList.add(MediaType.TEXT_HTML);
                    //将已经加入了text/html的MediaType支持列表设置为其支持的媒体类型列表
                    ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return builder.build();
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .ignoreAcceptHeader(true)
                .parameterName("mediaType")
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600 * 24);
    }
}
