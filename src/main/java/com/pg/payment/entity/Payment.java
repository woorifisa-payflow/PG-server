package com.pg.payment.entity;

import com.pg.merchant.entity.Merchant;
import com.pg.payment.enumtype.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uq_payment_merchant_order", columnNames = {"merchant_id", "order_id"})
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String paymentUid; // 우리가 생성하는 TID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    private String orderId; // 가맹점 주문번호
    private String productName;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentMethod;
    private String approvalCode;
    private String failureCode;
    private String failureMessage;

    private Long cancelledAmount = 0L;

    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 결제 성공 시 상태 업데이트
    public void approve(String approvalCode, LocalDateTime approvedAt) {
        this.status = PaymentStatus.SUCCESS;
        this.approvalCode = approvalCode;
        this.approvedAt = approvedAt;
    }
}