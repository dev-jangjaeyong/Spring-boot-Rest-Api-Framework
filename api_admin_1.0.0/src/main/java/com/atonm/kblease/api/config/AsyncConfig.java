package com.atonm.kblease.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/*
    @EnableAsync : spring의 메소드의 비동기 기능을 활성화 해준다.
    ThreadPoolTaskExecutor로 비동기로 호출하는 Thread 대한 설정을 한다.
    corePoolSize: 기본적으로 실행을 대기하고 있는 Thread의 갯수
    MaxPoolSise: 동시 동작하는, 최대 Thread 갯수
    QueueCapacity : MaxPoolSize를 초과하는 요청이 Thread 생성 요청시 해당 내용을 Queue에 저장하게 되고, 사용할수 있는 Thread 여유 자리가 발생하면 하나씩 꺼내져서 동작하게 된다.
    ThreadNamePrefix: spring이 생성하는 쓰레드의 접두사를 지정한다.
*/
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("***_");
        executor.initialize();
        return executor;
    }
}