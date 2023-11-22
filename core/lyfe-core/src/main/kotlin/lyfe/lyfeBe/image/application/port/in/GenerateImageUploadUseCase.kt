package lyfe.lyfeBe.image.application.port.`in`

import lyfe.lyfeBe.image.domain.GetImageUploadUrlResult


fun interface GenerateImageUploadUseCase {
    fun generateImageUploadUrl(format: String, path: String): GetImageUploadUrlResult
}
