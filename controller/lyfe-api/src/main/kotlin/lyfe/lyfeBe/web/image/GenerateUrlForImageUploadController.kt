//package lyfe.lyfeBe.web.image
//
//import lyfe.lyfeBe.dto.CommonResponse
//import lyfe.lyfeBe.image.port.`in`.GenerateImageUploadUseCase
//import lyfe.lyfeBe.image.GetImageUploadUrlResult
//import org.springframework.web.bind.annotation.*
//
//@RestController
//class GenerateUrlForImageUploadController(
//    private val generateImageUploadUseCase: GenerateImageUploadUseCase
//) {
//
//    @GetMapping("/v1/images/get-upload-url")
//    fun getUploadUrl(
//        @RequestParam format: String,
//        @RequestParam path: String
//    ): CommonResponse<GetImageUploadUrlResult> {
//
//        return CommonResponse(generateImageUploadUseCase.generateImageUploadUrl(format = format, path = path))
//    }
//
//}
