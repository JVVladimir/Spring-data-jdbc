package com.jvvladimir.spring.jdbc.cache

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.stereotype.Component


@Component
class SimpleCacheCustomizer : CacheManagerCustomizer<ConcurrentMapCacheManager> {

    override fun customize(cacheManager: ConcurrentMapCacheManager) {
        cacheManager.setCacheNames(listOf("customers"))
    }
}