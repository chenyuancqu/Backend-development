package domin;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author: chenyuan
 * @date: 2025/10/28
 * @description:
 */



/**
 * 模拟 Redis 分布式锁
 * 实际项目中可以用 Redisson 实现分布式锁。
 */
public class RedisDistributedLock {

    private static final RedisDistributedLock INSTANCE = new RedisDistributedLock();

    // 模拟 redis key-value 结构
    private final ConcurrentHashMap<String, Boolean> locks = new ConcurrentHashMap<>();

    private RedisDistributedLock() {}

    public static RedisDistributedLock getSingletonIntance() {
        return INSTANCE;
    }

    /**
     * 加锁操作（非阻塞）
     * @param key transactionId
     * @return 是否加锁成功
     */
    public boolean lockTransction(String key) {
        return locks.putIfAbsent(key, true) == null;
    }

    /**
     * 解锁操作
     * @param key transactionId
     */
    public void unlockTransction(String key) {
        locks.remove(key);
    }
}
