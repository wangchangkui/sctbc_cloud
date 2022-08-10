package edu.sctbc.util;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wck
 * @version 1.0.0
 * @Description
 * @createTime 2022年08月10日 10:44:00
 */
@Component
@Setter
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPool {
    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "thread-student";
    private static final ThreadPoolTaskExecutor EXECUTOR = new ThreadPoolTaskExecutor();
    /**
     * 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     * 当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private int keepAliveTime;
    /**
     * 缓冲队列大小
     */
    private int queueCapacity;

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        EXECUTOR.setCorePoolSize(corePoolSize);
        EXECUTOR.setMaxPoolSize(maxPoolSize);
        EXECUTOR.setQueueCapacity(queueCapacity);
        EXECUTOR.setKeepAliveSeconds(keepAliveTime);
        EXECUTOR.setThreadNamePrefix(THREAD_NAME_PREFIX);
        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        EXECUTOR.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        EXECUTOR.initialize();
        return EXECUTOR;
    }

}
