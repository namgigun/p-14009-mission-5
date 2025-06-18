package com.back.domain.wiseSaying.service;

import com.back.AppContext;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingMemoryRepository;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.standard.dto.Page;
import com.back.standard.dto.Pageable;

import java.util.Optional;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository= AppContext.wiseSayingRepository;
    }

    public WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(content, author);

        wiseSayingRepository.save(wiseSaying);

        return wiseSaying;
    }

    public Page<WiseSaying> findForList(String keywordType, String keyword, Pageable pageable) {
        if (keyword.isBlank()) return wiseSayingRepository.findForList(pageable);

        return switch (keywordType) {
            case "content" -> wiseSayingRepository.findForListByContentContaining(keyword, pageable);
            case "author" -> wiseSayingRepository.findForListByAuthorContaining(keyword, pageable);
            default -> wiseSayingRepository
                    .findForListByContentContainingOrAuthorContaining(keyword, keyword, pageable);
        };
    }

    public boolean delete(int id) {
        Optional<WiseSaying> opWiseSaying = wiseSayingRepository.findById(id);

        if (opWiseSaying.isEmpty()) return false;

        wiseSayingRepository.delete(opWiseSaying.get());

        return true;
    }

    public Optional<WiseSaying> findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        wiseSayingRepository.save(wiseSaying);
    }

    public void archive() {
        wiseSayingRepository.archive();
    }
}