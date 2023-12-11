package lyfe.lyfeBe.web.policy

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/policys")
class GetPolicyController(
    val service: PolicyService
) {

    @GetMapping("/{type}")
    fun getPolicy(@PathVariable type: PolicyType) = CommonResponse(service.getPolicy(type))

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
