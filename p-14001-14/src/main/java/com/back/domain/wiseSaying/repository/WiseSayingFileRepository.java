package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.global.AppConfig;
import com.back.standard.dto.Page;
import com.back.standard.dto.Pageable;
import com.back.standard.util.Util;

import java.util.*;
import java.util.stream.Stream;

public class WiseSayingFileRepository implements WiseSayingRepository {
    public WiseSaying save(WiseSaying wiseSaying) {
        if(wiseSaying.isNew()) {
            int newId = getLastId() + 1;
            wiseSaying.setId(newId);
            setLastId(newId);
        }

        Map<String, Object> wiseSayingMap = wiseSaying.toMap();
        String wiseSayingJsonStr = Util.json.toString(wiseSayingMap);
        Util.file.set(getEntityFilePath(wiseSaying), wiseSayingJsonStr);

        return wiseSaying;
    }

    private String getEntityFilePath(WiseSaying wiseSaying) {
        return getEntityFilePath(wiseSaying.getId());
    }

    public String getEntityFilePath(int id) {
        return getTableDirPath() + "/%d.json".formatted(id);
    }

    private void setLastId(int newId) {
        Util.file.set(getLastIdFilePath(), String.valueOf(newId));
    }

    private int getLastId() {
        return Util.file.getAsInt(getLastIdFilePath(), 0);
    }

    private String getLastIdFilePath() {
        return getTableDirPath() + "/lastId.txt";
    }

    public Optional<WiseSaying> findById(int id) {
        String wiseSayingJsonStr = Util.file.get(getEntityFilePath(id), "");

        if(wiseSayingJsonStr.isBlank()) {
            return Optional.empty();
        }

        Map<String, Object> wiseSayingMap = Util.json.toMap(wiseSayingJsonStr);

        return Optional.of(new WiseSaying(wiseSayingMap));
    }

    public boolean delete(WiseSaying wiseSaying) {
        String filePath = getEntityFilePath(wiseSaying);
        return Util.file.delete(filePath);
    }

    @Override
    public Page<WiseSaying> findForList(Pageable pageable) {
        List<WiseSaying> filtered = findAll();
        return createPage(filtered, pageable);
    }

    private Page<WiseSaying> createPage(List<WiseSaying> wiseSayings, Pageable pageable) {
        int totalCount = wiseSayings.size();

        List<WiseSaying> content = wiseSayings
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .toList();


        return new Page<>(
                totalCount,
                pageable.getPageNo(),
                pageable.getPageSize(),
                content
        );
    }

     public List<WiseSaying> findAll() {
        return loadAllWiseSayings()
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    private Stream<WiseSaying> loadAllWiseSayings() {
        return Util.file.walkRegularFiles(
                getTableDirPath(),
                "\\d+\\.json"
        ).map(path -> Util.file.get(path.toString(), ""))
                .map(Util.json::toMap)
                .map(WiseSaying::new);
    }

    @Override
    public Page<WiseSaying> findForListByContentContaining(String keyword, Pageable pageable) {
        List<WiseSaying> filtered = findByContentContaining(keyword);
        return createPage(filtered, pageable);
    }

    private List<WiseSaying> findByContentContaining(String keyword) {
        return loadAllWiseSayings()
                .filter(w -> w.getContent().contains(keyword))
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    @Override
    public Page<WiseSaying> findForListByAuthorContaining(String keyword, Pageable pageable) {
        List<WiseSaying> filtered = findByAuthorContaining(keyword);
        return createPage(filtered, pageable);
    }

    private List<WiseSaying> findByAuthorContaining(String keyword) {
        return loadAllWiseSayings()
                .filter(w -> w.getAuthor().contains(keyword))
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    @Override
    public Page<WiseSaying> findForListByContentContainingOrAuthorContaining(String keyword1, String keyword2, Pageable pageable) {
        List<WiseSaying> filtered = findByContentOrAuthorContaining(keyword1, keyword2);
        return createPage(filtered, pageable);
    }

    private List<WiseSaying> findByContentOrAuthorContaining(String keyword1, String keyword2) {
        return loadAllWiseSayings()
                .filter(w -> w.getContent().contains(keyword1) || w.getAuthor().contains(keyword2))
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    public void clear() {
        Util.file.delete(getTableDirPath());
    }
}
