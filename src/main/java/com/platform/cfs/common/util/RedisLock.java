package com.platform.cfs.common.util;

import com.platform.cfs.common.util.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @ClassName RedisLock
 * @Description 
 * @author 刘非
 * @date 2017-09-30 18:07
 */
public class RedisLock {
    private static Logger log = LoggerFactory.getLogger(RedisLock.class);
    private String lockKey;
    private int expireMsecs= 5000;
    private volatile boolean locked = false;
    private RedisManager redisManager;


    public RedisLock( RedisManager redisManager,String lockKey) {
        this.lockKey = lockKey;
        this.redisManager = redisManager;
    }

    public RedisLock(RedisManager redisManager, String lockKey, int expireMsecs) {
        this.expireMsecs=expireMsecs;
        this.redisManager=redisManager;
        this.lockKey = lockKey;
    }

    /**
     * @Title lock
     * @Description 获取锁
     * 实现思路：在指定的重试次数中，客户端会去重试获取锁
     * 如果当前没有客户端使用锁，则新创建一把锁；如果已经产生锁了，并且不在当前客户端手中
     * 则去验证上把锁的到期时间是否过期，如果已过期则通过getset方式重新获取锁，
     * 并设定新锁的到期时间；没有获取锁的线程则在指定的重试次数里去尝试获取锁，
     * 直至上把锁的到期时间已过期（肯定会过期，因为设置的过期时间远小于等待重试的随机时间），
     * 会再次选一个线程来去获取锁，再有其他客户端连进来还是这个规律。
     * @author 刘非
     * @return boolean
     */
    public synchronized boolean lock() throws InterruptedException {
        //如果在10次重试也没获取到锁，那么只能表示网络繁忙，请稍后再试了，不过这是不可能发生的事情，
        // 循环会随机睡眠100~1000毫秒，必然会重新获取锁的
        Random random = new Random ();
        int whileNum=10;//循环总次数
        int cycle = 1;//获取锁次数
        int rnum=0;//随机数
        int sum=0;
        while (cycle <= whileNum) {
            log.info("当前客户端尝试获取分布式锁，第"+cycle+"次");
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (redisManager.setnx(lockKey, expiresStr)) {
                log.info("客户端获取分布式锁成功");
                locked = true;
                return true;
            }
            //获取上次上锁的时间
            String currentValueStr = redisManager.get(lockKey);
            //判断是否为空，不为空的情况下，当前时间大于上次的锁到期时间 表示已经过期 则需要重新获取锁，并设置新的锁到期时间
            log.info("获取上把锁的过期时间："+currentValueStr+",和当前时间进行比较，判断是否过期");
            if (currentValueStr != null && System.currentTimeMillis()>Long.parseLong(currentValueStr) ) {
                String oldValueStr = redisManager.getSet(lockKey, expiresStr);//获取上一个锁到期时间，并设置现在的锁到期时间
                // 如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    log.info("上把锁的已过期，当前客户端已重新获取锁，并设置了到期时间");
                    locked = true;
                    return true;
                }
            }
            int num=0;
            //判断是否是最后一次，如果不是则随机数和基数一致，否则用过期时长-所有循环的随机数
            if(cycle==whileNum){
                //最后一次 过期时长-所有获取锁的随机数
                num = expireMsecs-sum;
            }else{
                //基数=过期时长/循环次数/百分之十 乘 当前循环获取锁的次数
                int baseNum=expireMsecs/whileNum/10*cycle;
                //随机的数和基数一致
                rnum = random.nextInt(baseNum);
                //基数+随机数
                num =baseNum+rnum;
            }
            sum+=num;
            log.info("当前第"+cycle+"次,给当前线程随机生成睡眠时间："+num+"毫秒");
            Thread.sleep(num);
            cycle++;
        }
        return false;
    }

    /**
     * @Title unlock
     * @Description 释放锁
     * @author 刘非
     */
    public synchronized void unlock() {
        if (locked) {
            log.info("当前客户端释放分布式锁");
            redisManager.del(lockKey);
            locked = false;
        }
    }



}
