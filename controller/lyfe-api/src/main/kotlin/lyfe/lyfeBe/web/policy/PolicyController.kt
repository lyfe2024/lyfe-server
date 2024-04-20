package lyfe.lyfeBe.web.policy

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate
import lyfe.lyfeBe.policy.dto.PolicyDto
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/policy")
class PolicyController(
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


    @GetMapping("/{type}")
    fun getPolicy(@PathVariable type: String): CommonResponse<PolicyDto> {
        val policyType = PolicyType.valueOf(type.uppercase(Locale.getDefault()))
        return CommonResponse(service.getPolicy(policyType))
    }

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
