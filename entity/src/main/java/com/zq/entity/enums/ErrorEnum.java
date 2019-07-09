package com.zq.entity.enums;

public enum ErrorEnum {
    ID_NOT_NULL("F001", "编号不可为空", false),
    NAME_NOT_NULL("F002", "名称不可为空", false),
    THRESHOLD_AMOUNT_NOT_NULL("F003", "起投金额不可为空", false),
    STEP_AMOUNT_NOT_NULL("F004", "投资步长不可为空", false),
    LOCK_TERM_NOT_NULL("F005", "锁定期不可为空", false),
    REWARD_RATE_NOT_NULL("F006", "收益率不可为空", false),
    STATUS_NOT_NULL("F007", "状态不可为空", false),
    UNKNOWN("999", "未知异常", false);

    private String code;
    private String message;
    private boolean canRetry;

    ErrorEnum(String code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }

    public static ErrorEnum getByMessage(String Message) {
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if (errorEnum.message.equals(Message)) {
                return errorEnum;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCanRetry() {
        return canRetry;
    }
}