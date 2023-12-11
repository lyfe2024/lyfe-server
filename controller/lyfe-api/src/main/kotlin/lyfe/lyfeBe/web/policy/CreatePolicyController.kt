package lyfe.lyfeBe.web.policy

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/policys")
class CreatePolicyController(
    val service: PolicyService
) {
    @PostMapping
    fun createPolicy(@Valid @RequestBody req: PolicySaveRequest) =
        CommonResponse(
            service.create(
                PolicyCreate(
                    title = req.title,
                    content = req.content,
                    version = req.version,
                    policyType = req.policyType
                )
            )
        )
}
