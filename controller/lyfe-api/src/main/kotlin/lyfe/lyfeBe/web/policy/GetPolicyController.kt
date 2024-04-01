package lyfe.lyfeBe.web.policy

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.dto.PolicyDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/policy")
class GetPolicyController(
    val service: PolicyService
) {

    @GetMapping("/{type}")
    fun getPolicy(@PathVariable type: String): CommonResponse<PolicyDto> {
        val policyType = PolicyType.valueOf(type.uppercase(Locale.getDefault()))
        return CommonResponse(service.getPolicy(policyType))
    }
}
