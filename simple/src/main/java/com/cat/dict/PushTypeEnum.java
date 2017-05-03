package com.cat.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送开锁信息类型
 */
@RequiredArgsConstructor
@Getter
public enum PushTypeEnum {
    FP("fp", "指纹开锁"),
    PWD("pwd", "密码开锁"),
    KEY("key", "钥匙开锁"),
    FP_ERR("fpErr", "指纹错误"),
    CARD_ERR("cardErr", "错误卡"),
    PWD_ERR("pwdErr", "密码错误"),
    LOW_BAT("lowBat", "低电量"),
    BROKEN("broken", "撬门"),
    NO_CLOSE("noClose", "长时未关门"),
    HELP("help", "胁迫报警"),
    CLOSE("closeLock", "闭锁"),
    CARD("card", "普通卡"),
    CARD_A("cardA", "管理卡"),
    CARD_M("cardM", "母卡"),
    CARD_E("cardE", "工程卡");

    private static final Map<String, PushTypeEnum> DB_MAP = new HashMap<>();
    private static final Map<String, PushTypeEnum> CMD_MAP = new HashMap<>();

    static {
        for (PushTypeEnum lockTypeEnum : values()) {
            DB_MAP.put(lockTypeEnum.getDescription(), lockTypeEnum);
            CMD_MAP.put(lockTypeEnum.getValue(), lockTypeEnum);
        }
    }

    private final String value;
    private final String description;

    public static PushTypeEnum from(String description) {
        return DB_MAP.get(description);
    }

    public static PushTypeEnum receive(String value) {
        return CMD_MAP.get(value);
    }
}
