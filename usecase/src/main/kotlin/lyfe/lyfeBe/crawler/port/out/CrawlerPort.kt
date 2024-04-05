package lyfe.lyfeBe.crawler.port.out

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.crawlerImage.CrawlerImage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CrawlerPort {
    fun get(keyword: String): List<CrawlerImage>

}
