package boki.redisinspring.board;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // @Cacheable(cacheNames = "getBoards", key = "'boards:page:' + #page + ':size' + #size", cacheManager = "boardCacheManager")
    @Cacheable(cacheNames = "getBoards", key = "T(boki.redisinspring.board.BoardService).createCacheKey(#page, #size)", cacheManager = "boardCacheManager")
    public List<Board> getBoards(int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<Board> pageOfBoards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return pageOfBoards.getContent();
    }

    @SuppressWarnings("unused")
    public static String createCacheKey(int page, int size) {
        return String.format("boards:page:%d:size:%d", page, size);
    }

}
