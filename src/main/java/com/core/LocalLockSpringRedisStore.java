package com.core;


import com.core.factory.CountLimitFacade;
import com.enums.CountFactoryEnum;
import com.util.CountLimitDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本地锁spring redis存储
 *
 * @author tingmailang
 */
@Component
public class LocalLockSpringRedisStore extends CountLimitCommonBusiness implements CountLimitFacade<CountLimitDTO> {

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public Boolean matching(CountFactoryEnum factory) {
        return Objects.equals(CountFactoryEnum.LOCAL_LOCK_SPRING_REDIS_STORE, factory);
    }

    @Override
    public boolean process(CountLimitDTO countLimitDTO) {
        if (countLimitDTO.getIsAdd()) {
            return this.checkExceed(countLimitDTO.getKey(), countLimitDTO.getCount(), countLimitDTO.getLimit());
        } else {
            return this.reduce(countLimitDTO.getKey(), countLimitDTO.getCount());
        }
    }

    /**
     * 检查是否超出计算限制
     *
     * @param key
     * @param count
     * @param limit
     * @return
     */
    public boolean checkExceed(String key, int count, int limit) {
        try {
            if (lock.tryLock()) {
                return super.springRedisCheckExceed(key, count, limit);
            } else {
                return false;
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    /**
     * 减少目前在查询中的参数量级
     *
     * @param key
     * @param count
     */
    public boolean reduce(String key, int count) {
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                return super.localReduce(key, count);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return false;
    }

}
