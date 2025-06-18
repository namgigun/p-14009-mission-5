package com.back.domain.wiseSaying.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
public class WiseSaying {
    private int id;
    private String content;
    private String author;


    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public boolean isNew() {
        return getId() == 0;
    }

    // 같은 정보를 가진 객체라면 같은 객체로 인정해준다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WiseSaying that = (WiseSaying) o;
        return getId() == that.getId() && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getAuthor());
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("id", id);
        map.put("content", content);
        map.put("author", author);

        return map;
    }

    public WiseSaying(Map<String, Object> wiseSayingMap) {
        id = (int) wiseSayingMap.get("id");
        content = (String) wiseSayingMap.get("content");
        author = (String) wiseSayingMap.get("author");
    }
}
