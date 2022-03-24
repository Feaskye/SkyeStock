package com.skyestock.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent e) {
        StackTraceElement st=e.getCallerData()[0];
        if(st.getClassName().startsWith("com.skyestock")){
            ExecutorService executorService= Executors.newCachedThreadPool();
            executorService.execute(()->{
                //异步写log
            });
        }
    }
}
