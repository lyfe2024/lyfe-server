package lyfe.lyfeBe.crawler

import lyfe.lyfeBe.crawler.dto.ImageCrawlerDto
import lyfe.lyfeBe.crawler.port.out.CrawlerPort
import org.springframework.stereotype.Service

@Service
class ImageCrawlerService(
    private val crawlerPort: CrawlerPort
) {
    fun get(imageCrawlerDto: ImageCrawlerDto) = crawlerPort.get(imageCrawlerDto.keyword)

}