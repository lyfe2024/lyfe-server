package lyfe.lyfeBe.web.policy

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate
import lyfe.lyfeBe.web.policy.req.PolicySaveReq
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/policys")
class PolicyController(
    val policyService: PolicyService
) {

    @GetMapping("/{type}")
    fun getPolicy(@PathVariable type: PolicyType) = CommonResponse(policyService.getPolicy(type))

    @PostMapping
    fun createPolicy(@Valid @RequestBody req: PolicySaveReq) =
        CommonResponse(
            policyService.create(
                PolicyCreate(
                    title = req.title,
                    content = req.content,
                    version = req.version,
                    policyType = req.policyType
                )
            )
        )


    @PutMapping("{policyId}")
    fun updatePolicy(
        @PathVariable(value = "policyId") policyId: Long,
        @Valid @RequestBody req: PolicyUpdateReq
    ) =
        CommonResponse(
            policyService.update(
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
