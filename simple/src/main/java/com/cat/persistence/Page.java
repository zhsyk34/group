package com.cat.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Page {

    public static final int DEFAULT_SIZE = 10;

    private final int offset;
    private final int limit;

    public static Page from(int pageNo, int pageSize) {
        assert pageNo > 0;
        assert pageSize > 0;
        return Page.of((pageNo - 1) * pageSize, pageSize);
    }

    public RowBounds row() {
        return new RowBounds(this.offset, this.limit);
    }

    public int pageNo() {
        return offset / limit + 1;
    }

    public int pageSize() {
        return limit;
    }

}
