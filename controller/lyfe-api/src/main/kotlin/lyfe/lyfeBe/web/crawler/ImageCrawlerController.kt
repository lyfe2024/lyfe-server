package lyfe.lyfeBe.web.crawler

import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.crawler.ImageCrawlerService
import lyfe.lyfeBe.crawler.dto.ImageCrawlerDto
import lyfe.lyfeBe.crawlerImage.CrawlerImage
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/image-crawler")
class ImageCrawlerController(
    private val service: ImageCrawlerService
) {


    @GetMapping("/{keyword}")
    fun getBoards(
        @PathVariable keyword: String
    ): CommonResponse<List<CrawlerImage>> {
        return CommonResponse(service.get(ImageCrawlerDto(keyword)))
    }
}