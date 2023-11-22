package lyfe.lyfeBe.web.image

import lyfe.lyfeBe.image.domain.Image

object ImageMapper {

    fun mapToResponse(image: Image): ImageResponse =
        ImageResponse(
            url = image.url,
            width = image.width,
            height = image.height
        )

}
