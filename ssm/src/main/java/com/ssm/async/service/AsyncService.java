package com.ssm.async.service;

public interface  AsyncService {
    /**
     * 异步执行耗时较长的操作
     *
     * @param cacheKey
     * @throws Exception
     */
    void asyncMethod(String cacheKey) throws Exception;

    /**
     * 获取执行进度
     *
     * @param cacheKey
     * @return
     * @throws Exception
     */
    String getProcess(String cacheKey) throws Exception;

    /**
     * 执行完成后，清除缓存
     *
     * @param cacheKey
     * @throws Exception
     */
    void clearCache(String cacheKey) throws Exception;
}
