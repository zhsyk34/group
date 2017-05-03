package com.cat.dict;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequestStatusEnum {
    ACCEPT(201, "接受请求"),//create
    REFUSE(401, "拒绝请求"),//refuse
    SUBMIT(202, "提交请求"),//submit for async ctrl
    SUCCESS(200, "请求成功"),//finish success
    FAIL(500, "请求失败"),//finish fail
    CALLBACK(203, "完成回调");//callback

    private final int code;
    @NonNull
    private final String description;

    public static RequestStatusEnum from(int code) {
        for (RequestStatusEnum statusEnum : values()) {
            if (code == statusEnum.code) {
                return statusEnum;
            }
        }
        return null;
    }
}
