package lyfe.lyfeBe.persistence.crawler

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState
import lyfe.lyfeBe.crawler.port.out.CrawlerPort
import lyfe.lyfeBe.crawlerImage.CrawlerImage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.lang.Thread.sleep

@Component
class BingImageCrawler(
    @Value("\${bing.login.url}")
    private val loginUrl: String,

    @Value("\${bing.login.email}")
    private val email: String,

    @Value("\${bing.login.password}")
    private val password: String,

    @Value("\${bing.image.create.url}")
    private val imageCreateUrl: String
): CrawlerPort {
    companion object {
        const val DELAY_TIME = 5000.0
        const val LOGIN_TIMEOUT: Double = 0.0
        const val NAVIGATION_TIMEOUT: Double = 0.0

        private val logger: Logger = LoggerFactory.getLogger(BingImageCrawler::class.java)
    }

    override fun get(keyword: String): List<CrawlerImage> {
        val crawlerImages = mutableListOf<CrawlerImage>()

        try {
            Playwright.create().use { playwright ->
                val browser: Browser = playwright.chromium().launch(BrowserType.LaunchOptions().setHeadless(false))
                val context: BrowserContext = browser.newContext()
                val page: Page = context.newPage()

                login(page)
                navigateToImageCreatePage(page)
                generateImages(page, keyword)
                crawlerImages.addAll(extractImageUrls(page))

                context.close()
                browser.close()
            }
        } catch (e: Exception) {
            throw IllegalStateException("Error occurred during crawling: ${e.message}", e)
        }

        return crawlerImages
    }

    private fun login(page: Page) {
        page.navigate(loginUrl)
        page.fill("#i0116", email)
        page.click("#idSIButton9")
        page.waitForSelector("#i0118", Page.WaitForSelectorOptions().setTimeout(LOGIN_TIMEOUT))
        page.fill("#i0118", password)
        page.click("#idSIButton9")

        if (page.isVisible("#acceptButton")) {
            page.click("#acceptButton")
        }

        page.waitForLoadState()
    }

    private fun navigateToImageCreatePage(page: Page) {
        page.navigate(imageCreateUrl)
        page.click("#create_btn_c")
        page.waitForTimeout(DELAY_TIME)
        page.waitForLoadState(LoadState.NETWORKIDLE)
        page.click("a[title='개인 계정으로 로그인']", Page.ClickOptions().setTimeout(NAVIGATION_TIMEOUT))

        page.waitForSelector("#acceptButton", Page.WaitForSelectorOptions().setTimeout(NAVIGATION_TIMEOUT))

        // 로그인 상태 유지 버튼 클릭
        if (page.isVisible("#acceptButton")) {
            page.click("#acceptButton")
        }
        page.waitForSelector("input[name='q']")
    }

    private fun generateImages(page: Page, keyword: String) {
        page.fill("input[name='q']", keyword)
        page.click("a[role='button'][id='create_btn_c']")
        page.waitForSelector("img.mimg", Page.WaitForSelectorOptions().setTimeout(NAVIGATION_TIMEOUT))
    }

    private fun extractImageUrls(page: Page): List<CrawlerImage> {
        val imageElements: List<ElementHandle> = page.querySelectorAll("img.mimg")
        return imageElements.mapNotNull { elementHandle ->
            val imageUrl = elementHandle.getAttribute("src")
            imageUrl?.let { CrawlerImage(it) }
        }
    }
}