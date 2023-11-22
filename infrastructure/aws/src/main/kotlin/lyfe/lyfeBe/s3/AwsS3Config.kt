package lyfe.lyfeBe.s3

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsS3Config {
    @Value("\${cloud.aws.credentials.accessKey}")
    lateinit var accessKey: String

    @Value("\${cloud.aws.credentials.secretKey}")
    lateinit var secretKey: String

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var s3Bucket: String

    @Value("\${cloud.aws.region.static}")
    lateinit var region: String

    @Bean
    fun awsCredentials(): AWSCredentials =
        BasicAWSCredentials(accessKey, secretKey)

    @Bean
    fun amazonS3(): AmazonS3 =
        AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials()))
            .withRegion(region)
            .build()

}
