package lyfe.lyfeBe.image.port.`in`

import lyfe.lyfeBe.image.GetImageUploadUrlResult


fun interface GenerateImageUploadUseCase {
    fun generateImageUploadUrl(format: String, path: String): GetImageUploadUrlResult
}
