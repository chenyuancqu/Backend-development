package domin;

import javax.transaction.InvalidTransactionException;

/**
 * @author: chenyuan
 * @date: 2025/10/28
 * @description:
 */
public class Transaction {
    private String id;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private Long orderId;
    private Long createTimeStamp;
    private Double amount;
    private String status;
    private String walletTransactionId;

    // get() methods...

    public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, Long orderId) {
        if (preAssignedId != null && !preAssignedId.isEmpty()) {
            this.id = preAssignedId;
        } else {
            this.id = IdGenerator.generateTransactionId();
        }
        if (!this.id.startsWith("t_")) {
            this.id = "t_" + preAssignedId;
        }
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = "待付款";
        this.createTimeStamp = System.currentTimeMillis();
    }

    public boolean execute() throws InvalidTransactionException {
        if (buyerId == null || (sellerId == null || amount < 0.0)) {
            throw new InvalidTransactionException("参数错误");
        }
        if ("待付款".equals(status)) {
            return true;
        }
        boolean isLocked = false;
        try {
            isLocked = RedisDistributedLock.getSingletonIntance().lockTransction(id);
            if (!isLocked) {
                // 锁定未成功，返回false，job兜底执行
                return false;
            }
            if ("执行完成".equals(status)) {
                return true;
            }
            long executionInvokedTimeStamp = System.currentTimeMillis();
            if (executionInvokedTimeStamp - createTimeStamp > 1000) {
                this.status = "已过期";
                return false;
            }
            WalletRpcService walletRpcService = new WalletRpcService();
            String walletTransactionId = walletRpcService.moveMoney(id, buyerId, sellerId, amount);
            if (walletTransactionId != null) {
                this.walletTransactionId = walletTransactionId;
                this.status = "执行完成";
                return true;
            } else {
                this.status = "失败";
                return false;
            }
        } finally {
            if (isLocked) {
                RedisDistributedLock.getSingletonIntance().unlockTransction(id);
            }
        }
    }
}
