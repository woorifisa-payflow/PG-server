package com.pg.merchant.entity;

import com.pg.common.exception.BusinessException;
import com.pg.common.exception.ErrorCode;
import com.pg.payment.enumtype.MerchantStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_uid", unique = true, nullable = false)
    private String merchantUid;

    private String name;

    @Column(name = "api_key", unique = true, nullable = false)
    private String apiKey;

    private String callbackUrl;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status; // ACTIVE, INACTIVE

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 가맹점 검증 로직
    public void validate() {
        if (this.status != MerchantStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.MERCHANT_INACTIVE);
        }
    }
}
