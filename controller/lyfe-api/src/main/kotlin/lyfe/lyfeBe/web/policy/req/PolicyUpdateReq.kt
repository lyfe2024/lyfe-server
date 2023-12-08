package lyfe.lyfeBe.web.policy.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lyfe.lyfeBe.policy.PolicyType


data class PolicyUpdateReq(
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,
    @NotBlank
    val version: String,
    @NotNull
    val policyType: PolicyType
)