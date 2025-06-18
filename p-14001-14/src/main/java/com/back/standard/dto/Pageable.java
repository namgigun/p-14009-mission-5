package com.back.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
@Setter
public class Pageable {
    private int pageNo;
    private int pageSize;

    public long getSkipCount() {
        return (pageNo - 1) * pageSize;
    }
}
