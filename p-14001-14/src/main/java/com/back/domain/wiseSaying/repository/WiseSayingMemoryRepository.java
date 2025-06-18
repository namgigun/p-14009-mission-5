package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.standard.dto.Page;
import com.back.standard.dto.Pageable;

import java.util.*;
import java.util.stream.IntStream;

public class WiseSayingMemoryRepository implements WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying save(WiseSaying wiseSaying) {
        if (wiseSaying.isNew()) {
            wiseSaying.setId(++lastId);
            wiseSayings.add(wiseSaying);
        }

        return wiseSaying;
    }

    public Page<WiseSaying> findForList(Pageable pageable) {
        List<WiseSaying> reversedList = new ArrayList<>(wiseSayings);
        Collections.reverse(reversedList);
        
        int totalCount = reversedList.size();

        List<WiseSaying> content = reversedList
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .toList();

        return new Page<>(totalCount, pageable.getPageNo(), pageable.getPageSize(), content);
    }

    public int findIndexById(int id) {
        return IntStream
                .range(0, wiseSayings.size())
                .filter(index -> wiseSayings.get(index).getId() == id)
                .findFirst()
                .orElse(-1);
    }

    public Optional<WiseSaying> findById(int id) {
        int index = findIndexById(id);

        if (index == -1) return Optional.empty();

        return Optional.of(wiseSayings.get(index));
    }

    public boolean delete(WiseSaying wiseSaying) {
        return wiseSayings.remove(wiseSaying);
    }

    public Page<WiseSaying> findForListByContentContaining(String keyword, Pageable pageable) {
        List<WiseSaying> reversedList = new ArrayList<>(wiseSayings);
        Collections.reverse(reversedList);

        List<WiseSaying> filtered = reversedList
                .stream()
                .filter(
                        w -> w.getContent().contains(keyword)
                )
                .toList();

        int totalCount = filtered.size();

        List<WiseSaying> content = filtered
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .toList();

        return new Page<>(totalCount, pageable.getPageNo(), pageable.getPageSize(), content);
    }

    public Page<WiseSaying> findForListByAuthorContaining(String keyword, Pageable pageable) {
        List<WiseSaying> reversedList = new ArrayList<>(wiseSayings);
        Collections.reverse(reversedList);

        List<WiseSaying> filtered = reversedList
                .stream()
                .filter(
                        w -> w.getAuthor().contains(keyword)
                )
                .toList();

        int totalCount = filtered.size();

        List<WiseSaying> content = filtered
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .toList();

        return new Page<>(totalCount, pageable.getPageNo(), pageable.getPageSize(), content);
    }

    public Page<WiseSaying> findForListByContentContainingOrAuthorContaining(String keyword1, String keyword2,
                                                                             Pageable pageable) {
        List<WiseSaying> reversedList = new ArrayList<>(wiseSayings);
        Collections.reverse(reversedList);

        List<WiseSaying> filtered = reversedList
                .stream()
                .filter(
                        w -> w.getContent().contains(keyword1) || w.getAuthor().contains(keyword2)
                )
                .toList();

        int totalCount = filtered.size();

        List<WiseSaying> content = filtered
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .toList();

        return new Page<>(totalCount, pageable.getPageNo(), pageable.getPageSize(), content);
    }

    @Override
    public List<WiseSaying> findAll() {
        List<WiseSaying> reversedList = new ArrayList<>(wiseSayings);
        Collections.reverse(reversedList);

        return reversedList;
    }
}