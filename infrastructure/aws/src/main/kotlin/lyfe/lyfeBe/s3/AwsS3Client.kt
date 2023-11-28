package lyfe.lyfeBe.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import lyfe.lyfeBe.image.GetImageUploadUrlResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.Duration
import java.time.ZonedDateTime
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
        log.info { "Generating pre-signed URL" }

        val expiresAt = ZonedDateTime.now() + expiresAfter
        val objectKey = generateObjectKey(format, path)
        val preSignedUrl = generatePreSignedUrl(objectKey, expiresAt, format)

        log.info { "Generated pre-signed URL: $preSignedUrl" }

        return GetImageUploadUrlResult(
            url = preSignedUrl,
            key = objectKey,
            expiresAt = expiresAt
        )
    }

    private fun generatePreSignedUrl(objectKey: String, expiresAt: ZonedDateTime, format: String): String {
        val generatePreSignedUrlRequest = GeneratePresignedUrlRequest(s3Bucket, objectKey)
            .withMethod(HttpMethod.PUT)
            .withExpiration(Date.from(expiresAt.toInstant()))
            .withContentType("image/$format")

        return amazonS3.generatePresignedUrl(generatePreSignedUrlRequest).toString()
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
