package lyfe.lyfeBe.web.topic.req

import jakarta.validation.constraints.NotBlank

class UpdateTopicRequest(
    @NotBlank
    val content: String

) {

}