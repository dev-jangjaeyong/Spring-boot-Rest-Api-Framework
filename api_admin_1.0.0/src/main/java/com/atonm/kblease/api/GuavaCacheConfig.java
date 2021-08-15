package com.atonm.kblease.api;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

//import org.springframework.cache.guava.GuavaCacheManager;

@Configuration
@EnableCaching
public class GuavaCacheConfig extends CachingConfigurerSupport {
	@Override
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		return cacheManager;
	}

	@Bean
	public CacheManager timeoutCacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
			.maximumSize(10000)
			.expireAfterWrite(5, TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}

	@Bean
	public CacheManager timeoutCacheManagerOneMinute() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
			.maximumSize(10000)
			.expireAfterWrite(1, TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}

}

