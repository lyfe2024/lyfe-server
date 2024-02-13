package lyfe.lyfeBe.auth.dto.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleUserInfoResult(
    val sub: String,
    val name: String,
    @JsonProperty("given_name")
    val givenName: String,
    @JsonProperty("family_name")
    val familyName: String,
    val picture: String,
    val email: String,
    @JsonProperty("email_verified")
    val emailVerified: Boolean,
    val locale: String
)
