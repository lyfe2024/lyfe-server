package lyfe.lyfeBe.web.policy

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyUpdate
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/policys")
class UpdatePolicyController(
    val service: PolicyService
) {
    @PutMapping("{policyId}")
    fun updatePolicy(
        @PathVariable(value = "policyId") policyId: Long,
        @Valid @RequestBody req: PolicyUpdateReq
    ) =
        CommonResponse(
            service.update(
                PolicyUpdate(
                    id = policyId,
                    title = req.title,
                    content = req.content,
                    version = req.version,
                    policyType = req.policyType
                )
            )
        )
}
