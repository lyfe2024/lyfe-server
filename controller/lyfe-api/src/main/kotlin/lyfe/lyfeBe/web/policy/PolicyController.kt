package lyfe.lyfeBe.web.policy

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.dto.PolicyDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/policys")
class PolicyController(
     val policyService: PolicyService
) {

    @GetMapping("/{type}")
    fun getPolicy(@PathVariable type: PolicyType) = CommonResponse(policyService.getPolicy(type))
}