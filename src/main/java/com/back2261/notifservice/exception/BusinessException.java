package com.back2261.notifservice.exception;

import com.back2261.notifservice.interfaces.enums.TransactionCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final TransactionCode transactionCode;

    public BusinessException(TransactionCode transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public String getMessage() {

        return "Error:" + transactionCode.name() + "\n" + "ErrorId:" + transactionCode.getId() + "\n" + "ErrorCode:"
                + transactionCode.getCode();
    }
}
