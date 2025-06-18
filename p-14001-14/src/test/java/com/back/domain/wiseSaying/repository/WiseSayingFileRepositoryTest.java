package com.back.domain.wiseSaying.repository;

import com.back.AppContext;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.global.AppConfig;
import com.back.standard.dto.Pageable;
import com.back.standard.util.Util;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class WiseSayingFileRepositoryTest {
    private final WiseSayingFileRepository wiseSayingFileRepository;
    WiseSayingFileRepositoryTest() {
        wiseSayingFileRepository = AppContext.wiseSayingFileRepository;
    }
    @BeforeAll
    static void beforeAll() {
        AppConfig.setTestMode();
        AppContext.renew();
    }
    @BeforeEach
    void beforeEach() {
        wiseSayingFileRepository.clear();
    }
    @Test
    @DisplayName("저장")
    void t1() {
        WiseSaying wiseSaying = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying);

        WiseSaying foundWiseSaying = wiseSayingFileRepository.findById(1).get();

        assertThat(foundWiseSaying).isEqualTo(wiseSaying);
    }

    @Test
    @DisplayName("2번째 등록에서는 2번 명언이 생산된다")
    void t2() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 죽음을 적들에게 알리지 말라.", "나폴레옹");
        wiseSayingFileRepository.save(wiseSaying2);

        assertThat(wiseSaying2.getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("명언 삭제")
    void t3() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 죽음을 적들에게 알리지 말라.", "나폴레옹");
        wiseSayingFileRepository.save(wiseSaying2);

        wiseSayingFileRepository.delete(wiseSaying2);

        Optional<WiseSaying> opFoundWiseSaying = wiseSayingFileRepository.findById(2);

        assertThat(opFoundWiseSaying).isEmpty();
    }

    @Test
    @DisplayName("명언 다건조회")
    void t4() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        assertThat(
                wiseSayingFileRepository.findForList(new Pageable(1, 5)).getContent()
        ).containsExactly(wiseSaying2, wiseSaying1);
    }

    @Test
    @DisplayName("명언 다건조회, findForListByContentContaining")
    void t5() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        WiseSaying wiseSaying3 = new WiseSaying("생생한 꿈은 현실이 된다.", "작자미상");
        wiseSayingFileRepository.save(wiseSaying3);

        assertThat(
                wiseSayingFileRepository.findForListByContentContaining("꿈", new Pageable(1, 5)).getContent()
        ).containsExactly(wiseSaying3, wiseSaying1);
    }

    @Test
    @DisplayName("명언 다건조회, findForListByAuthorContaining")
    void t6() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        WiseSaying wiseSaying3 = new WiseSaying("생생한 꿈은 현실이 된다.", "작자미상");
        wiseSayingFileRepository.save(wiseSaying3);

        WiseSaying wiseSaying4 = new WiseSaying("신은 주사위놀이를 하지 않는다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying4);

        assertThat(
                wiseSayingFileRepository.findForListByAuthorContaining("아인슈타인", new Pageable(1, 5)).getContent()
        ).containsExactly(wiseSaying4, wiseSaying2);
    }

    @Test
    @DisplayName("명언 다건조회, findForListByContentContainingOrAuthorContaining")
    void t7() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        WiseSaying wiseSaying3 = new WiseSaying("생생한 꿈은 현실이 된다.", "작자미상");
        wiseSayingFileRepository.save(wiseSaying3);

        WiseSaying wiseSaying4 = new WiseSaying("신은 주사위놀이를 하지 않는다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying4);

        WiseSaying wiseSaying5 = new WiseSaying("나의 상상은 현실이 된다.", "아무개");
        wiseSayingFileRepository.save(wiseSaying5);

        assertThat(
                wiseSayingFileRepository.findForListByContentContainingOrAuthorContaining("상", "상", new Pageable(1, 5)).getContent()
        ).containsExactly(wiseSaying5, wiseSaying3);
    }

    @Test
    @DisplayName("빌드를 하면 data.json 파일이 생성된다.")
    void t8() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        String filePath = wiseSayingFileRepository.archive();

        assertThat(
                Util.file.exists(filePath)
        ).isTrue();
    }

    @Test
    @DisplayName("빌드 시 생성되는 data.json은 배열의 형태이다.")
    void t9() {
        WiseSaying wiseSaying1 = new WiseSaying("꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.", "괴테");
        wiseSayingFileRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying("나의 삶의 가치는 나의 결정에 달려있다.", "아인슈타인");
        wiseSayingFileRepository.save(wiseSaying2);

        String filePath = wiseSayingFileRepository.archive();

        String jsonStr = Util.file.get(filePath, "");

        assertThat(
                jsonStr
        ).isEqualTo("""
                [
                    {
                        "id": 2,
                        "content": "나의 삶의 가치는 나의 결정에 달려있다.",
                        "author": "아인슈타인"
                    },
                    {
                        "id": 1,
                        "content": "꿈을 지녀라. 그러면 어려운 현실을 이길 수 있다.",
                        "author": "괴테"
                    }
                ]
                """.stripIndent().trim());
    }
}