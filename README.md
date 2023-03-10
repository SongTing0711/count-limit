# count-limit

#### 介绍
       许多需求计算量都在扩大， 针对大容量、高并发的接口、mq或者其他计算方法，同一时间在运行的计算维度进行限制，相当于把资源到计算的对象维度。
        不同于参数限制，工具针对的是服务所有线程对于该计算维度的限流。

#### 软件架构
![image2022-5-11_18-12-51 (1)](https://user-images.githubusercontent.com/90837835/219278302-b6a44f15-7c96-45b6-8ea9-ae2724c82616.png)

SpringBoot


#### 使用说明

    1、计算量的存储与并发控制主要分为redis和本地两种，目前支持链接redis的工具主要是redisson、spring redis
    2、因此有以下七种使用方式，默认使用ReentrantLock加锁，本地map缓存
    3、如果使用工具的地方很多，存储比较适合使用redis
    4、如果限流的方法qps很高，使用redis进行加锁处理可能是比ReentrantLock更好的选择
    
    1、ReentrantLock加锁，本地map缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    2、ReentrantLock加锁，redisson缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.LOCAL_LOCK_REDISSON_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    
    3、ReentrantLock加锁，spring redis缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.LOCAL_LOCK_SPRING_REDIS_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    4、redisson加锁，本地map缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.REDISSON_LOCK_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    5、redisson加锁，redisson缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.REDISSON_LOCK_REDISSON_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    6、spring redis加锁，本地map缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.SPRING_REDIS_LOCK_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    7、spring redis加锁，spring redis缓存
    
    @CountLimit(objectName = "request", 
        paramName = "shopIdList", 
        limit = 20000, 
        countFactoryEnum = CountLimitCommonUtil.SPRING_REDIS_LOCK_SPRING_REDIS_STORE)
    public void audit(ShopOnlineDTO request) {
        //业务处理
    }
    
    
    
