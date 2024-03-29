package lyfe.lyfeBe.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import lyfe.lyfeBe.image.GetImageUploadUrlResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.time.Duration
import java.util.*

@Component
class AwsS3Client(
    private val amazonS3: AmazonS3,
    @Value("\${cloud.aws.s3.bucket}") private val s3Bucket: String,
    @Value("\${environment}") private val environment: String
) {

    private val log = KotlinLogging.logger { }

    private val expiresAfter: Duration = Duration.ofHours(1)
    private val objectKeyLength: Int = 16
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val random: SecureRandom = SecureRandom()

    fun generateImageUploadUrl(format: String, path: String): GetImageUploadUrlResult {
        val objectKey = generateObjectKey(format, path)
        val generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(objectKey)
        val preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
        val decodedUrl = URLDecoder.decode(preSignedUrl.toString(), StandardCharsets.UTF_8.toString())
        return GetImageUploadUrlResult(
            url = decodedUrl,
            key = objectKey
        )
    }

    private fun getGeneratePreSignedUrlRequest(fileName: String): GeneratePresignedUrlRequest {
        val expiration = getPreSignedUrlExpiration()
        return GeneratePresignedUrlRequest(s3Bucket, fileName)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration).apply {
                addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString())
            }
    }

    private fun getPreSignedUrlExpiration(): Date {
        val expiration = Date()
        val expTimeMillis = expiration.time + 1000 * 60 * 2 // 2 minutes
        expiration.time = expTimeMillis
        return expiration
    }

    private fun generateObjectKey(format: String, path: String): String {
        val environmentPath = determineEnvironmentPath()
        val key = generateRandomString(objectKeyLength)
        return "$environmentPath/$path/$key.$format"
    }

    private fun generateRandomString(length: Int): String {
        return (1..length)
            .map { charPool[random.nextInt(charPool.size)] }
            .joinToString("")
    }

    private fun determineEnvironmentPath(): String {
        return when (environment) {
            "ec2-dev" -> "dev"
            "local" -> "local"
            else -> {
                log.error { "Wrong environment: $environment" }
                return "default"
            }
        }
    }
}
