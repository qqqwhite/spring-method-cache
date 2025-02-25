package org.example.springmethodcache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodCacheAspect {

    Cache cache = new Cache();

    @Around("@annotation(methodCache)")
    public Object around(ProceedingJoinPoint joinPoint, MethodCache methodCache) throws Throwable {
        Object[] args = joinPoint.getArgs();
        boolean fastCache = methodCache.fastCache();
        cache.setFastCache(fastCache);
        Object result = cache.get(args);
        if (result == null) {
            result = joinPoint.proceed(args);
            cache.put(args, result);
        }
        return result;
    }
}
