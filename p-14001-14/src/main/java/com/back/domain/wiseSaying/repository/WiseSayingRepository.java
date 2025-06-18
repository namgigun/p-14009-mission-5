package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.global.AppConfig;
import com.back.standard.dto.Page;
import com.back.standard.dto.Pageable;
import com.back.standard.util.Util;

import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {
    WiseSaying save(WiseSaying wiseSaying);

    Optional<WiseSaying> findById(int id);

    boolean delete(WiseSaying wiseSaying);

    Page<WiseSaying> findForList(Pageable pageable);

    Page<WiseSaying> findForListByContentContaining(String keyword, Pageable pageable);

    Page<WiseSaying> findForListByAuthorContaining(String keyword, Pageable pageable);

    Page<WiseSaying> findForListByContentContainingOrAuthorContaining(String keyword1, String keyword2, Pageable pageable);

    List<WiseSaying> findAll();
    default String archive() {
        List<WiseSaying> all = findAll();

        String json = Util.json.toString(
                all.stream().map(WiseSaying::toMap)
                        .toList()
        );

        String filePath = getArchiveDirPath();

        Util.file.set(filePath, json);

        return filePath;
    }

    default String getTableDirPath() {
        return AppConfig.getMode() + "Db/wiseSaying";
    }

    default String getArchiveDirPath() {
        return getTableDirPath() + "/data.json";
    }
}
