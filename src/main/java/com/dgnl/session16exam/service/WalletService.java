package com.dgnl.session16exam.service;
import com.dgnl.session16exam.model.Wallet;
import com.dgnl.session16exam.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional(rollbackFor = Exception.class)
    public void transferMoney(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ví gửi"));
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        walletRepository.save(fromWallet);

//        Giả lập quăng lỗi hệ thống
        if (true) {
            throw new RuntimeException("Lỗi : Rollback ");
        }

        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ví nhận"));
        toWallet.setBalance(toWallet.getBalance().add(amount));
        walletRepository.save(toWallet);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSystemLog(String message) {
        System.out.println("Lưu Log thành công: " + message);

    }
}
