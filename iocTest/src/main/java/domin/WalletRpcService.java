package domin;

/**
 * @author: chenyuan
 * @date: 2025/10/28
 * @description:
 */

import java.util.UUID;

/**
 * 模拟 RPC 钱包服务，用于转账操作
 */
public class WalletRpcService {

    /**
     * 模拟转账操作
     * @param transactionId 交易ID
     * @param buyerId 买家ID
     * @param sellerId 卖家ID
     * @param amount 转账金额
     * @return 钱包交易ID（若失败返回null）
     */
    public String moveMoney(String transactionId, Long buyerId, Long sellerId, Double amount) {
        if (amount == null || amount <= 0) {
            return null;
        }

        // 模拟网络调用耗时
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        // 模拟成功概率 90%
        if (Math.random() < 0.9) {
            return "wallet_txn_" + UUID.randomUUID();
        } else {
            return null;
        }
    }
}
