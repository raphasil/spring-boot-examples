package com.raphasil.example.async.configuration.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomInterceptor implements AsyncHandlerInterceptor {

  private static final Logger LOG = LoggerFactory.getLogger(CustomInterceptor.class);

  private static final String CURRENT_HANDLE = "com.raphasil.currenthandle.start";
  private static final String PRE_HANDLE = "com.raphasil.prehandle.start";
  private static final String POST_HANDLE = "com.raphasil.posthandle.start";

  @Override
  public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    LOG.debug("BEGIN");
    request.setAttribute(CURRENT_HANDLE, System.nanoTime());
    LOG.debug("END");
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    LOG.debug("BEGIN");

    if (request.getAttribute(PRE_HANDLE) == null) {
      request.setAttribute(PRE_HANDLE, System.nanoTime());
      LOG.debug("set pre handle time");
    } else {
      LOG.debug("pre handle time already updated ");
    }

    LOG.debug("END");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    LOG.debug("BEGIN");
    request.setAttribute(POST_HANDLE, System.nanoTime());
    LOG.debug("END");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    LOG.debug("BEGIN");
    final long now = System.nanoTime();

    final Object currentHandle = request.getAttribute(CURRENT_HANDLE);
    final Object preHandle = request.getAttribute(PRE_HANDLE);
    final Object postHandle = request.getAttribute(POST_HANDLE);

    LOG.debug("pre handle - now: " + calcTime(preHandle, now));
    LOG.debug("current handle - now: " + calcTime(currentHandle, now));
    LOG.debug("post handle - now: " + calcTime(postHandle, now));

    LOG.debug("END");
  }

  private long calcTime(Object startTimeObj, long endTime) {

    if (startTimeObj == null) {
      return 0;
    }

    final long startTime = Long.parseLong(String.valueOf(startTimeObj));

    if (startTime <= 0 || endTime <= 0) {
      return 0;
    }

    return TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
  }
}
