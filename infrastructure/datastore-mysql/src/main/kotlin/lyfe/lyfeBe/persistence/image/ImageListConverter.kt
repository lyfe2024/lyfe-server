package lyfe.lyfeBe.persistence.image

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import lyfe.lyfeBe.image.domain.Image


@Converter(autoApply = true)
class ImageListConverter : AttributeConverter<List<Image>, String?> {

    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<Image>): String? {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): List<Image> {
        return dbData?.let { objectMapper.readValue(it) } ?: emptyList()
    }

}
