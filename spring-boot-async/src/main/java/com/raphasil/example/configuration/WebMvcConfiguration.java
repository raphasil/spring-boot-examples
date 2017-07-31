package com.raphasil.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by raphael on 30/07/17.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AsyncHandlerInterceptor() {

            private static final String CURRENT_HANDLE = "com.raphasil.currenthandle.start";
            private static final String PRE_HANDLE = "com.raphasil.prehandle.start";
            private static final String POST_HANDLE = "com.raphasil.posthandle.start";

            @Override
            public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println("Interceptor - afterConcurrent - BEGIN");
                request.setAttribute(CURRENT_HANDLE, System.nanoTime());
                System.out.println("Interceptor - afterConcurrent - END");
            }

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println("Interceptor - preHandle - BEGIN");

                if(request.getAttribute(PRE_HANDLE) == null)
                    request.setAttribute(PRE_HANDLE, System.nanoTime());

                System.out.println("Interceptor - preHandle - END");
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                System.out.println("Interceptor - postHandle - BEGIN");
                request.setAttribute(POST_HANDLE, System.nanoTime());
                System.out.println("Interceptor - postHandle - END");
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                System.out.println("Interceptor - afterCompletion - BEGIN");
                final long now = System.nanoTime();
                final Object currentHandle = request.getAttribute(CURRENT_HANDLE);
                final Object preHandle = request.getAttribute(PRE_HANDLE);
                final Object postHandle = request.getAttribute(POST_HANDLE);

                System.out.println("pre handle - now: " + calcTime(preHandle, now));
                System.out.println("current handle - now: " + calcTime(currentHandle, now));
                System.out.println("post handle - now: " + calcTime(postHandle, now));
                System.out.println("Interceptor - afterCompletion - END");
            }

            private long calcTime(Object startTimeObj, long endTime) {

                if(startTimeObj == null) {
                    return 0;
                }

                final long startTime = Long.parseLong(String.valueOf(startTimeObj));

                if(startTime <= 0 || endTime <= 0) {
                    return 0;
                }

                return TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
            }

        }).addPathPatterns("/**");
    }

}
