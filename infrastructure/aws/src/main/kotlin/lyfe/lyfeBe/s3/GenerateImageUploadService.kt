package lyfe.lyfeBe.s3


import lyfe.lyfeBe.image.GetImageUploadUrlResult
import lyfe.lyfeBe.image.port.`in`.GenerateImageUploadUseCase
import org.springframework.stereotype.Service

@Service
class GenerateImageUploadService(
    private val awsS3Client: AwsS3Client
) : GenerateImageUploadUseCase {

    override fun generateImageUploadUrl(format: String, path: String): GetImageUploadUrlResult {
        require(path == "topic_picture") { "이미지 경로가 올바르지 않습니다." }
        return awsS3Client.generateImageUploadUrl(format = format, path = path)
    }

}
