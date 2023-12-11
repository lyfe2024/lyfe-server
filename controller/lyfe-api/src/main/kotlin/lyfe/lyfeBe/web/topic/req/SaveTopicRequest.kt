package lyfe.lyfeBe.web.topic.req

import jakarta.validation.constraints.NotBlank

class SaveTopicRequest(
    @NotBlank
    val content: String

) {

}