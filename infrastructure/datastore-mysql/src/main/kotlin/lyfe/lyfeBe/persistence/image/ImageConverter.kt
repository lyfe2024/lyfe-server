package lyfe.lyfeBe.persistence.image

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import lyfe.lyfeBe.image.Image

@Converter(autoApply = true)
class ImageConverter : AttributeConverter<Image?, String?> {

    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Image?): String? {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): Image? {
        return dbData?.let { objectMapper.readValue(dbData) }
    }

}