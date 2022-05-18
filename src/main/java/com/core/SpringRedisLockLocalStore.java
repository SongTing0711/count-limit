package com.core;


import com.core.factory.CountLimitFacade;
import com.enums.CountFactoryEnum;
import com.redislock.annotation.RedisLock;
import com.redislock.core.RedisLockCommonUtil;
import com.util.CountLimitDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpringRedis锁本地存储
 *
 * @author tingmailang
 */
@Slf4j
@Component
public class SpringRedisLockLocalStore extends CountLimitCommonBusiness implements CountLimitFacade<CountLimitDTO> {


    @Override
    public Boolean matching(CountFactoryEnum factory) {
        return Objects.equals(CountFactoryEnum.SPRING_REDIS_LOCK_STORE, factory);
    }

    @Override
    @RedisLock(key = CountLimitCommonUtil.COUNT_LIMIT_LOCK,
            suffixKeyTypeEnum = RedisLockCommonUtil.PARAM,
            objectName = "countLimitDTO",
            paramName = "LockKey",
            redisEnum = RedisLockCommonUtil.SPRING_REDIS)
    public boolean process(CountLimitDTO countLimitDTO) {
        if (countLimitDTO.getIsAdd()) {
            return super.localCheckExceed(countLimitDTO.getKey(), countLimitDTO.getCount(), countLimitDTO.getLimit());
        } else {
            return super.localReduce(countLimitDTO.getKey(), countLimitDTO.getCount());
        }
    }


}
