package lyfe.lyfeBe.web.policy

import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/policys")
class PolicyMockController {

    @GetMapping("/terms")
    fun getTerms()
    : CommonResponse<PolicyResponse> {
        val terms = PolicyResponse(
            title = "이용약관",
            content = "이용약관 내용",
            version = "1.0.0"
        )
        return CommonResponse(terms)
    }

    @GetMapping("/personal-info-agree")
    fun getPersonalInfoAgree(): CommonResponse<PolicyResponse> {
        val personalInfoAgree = PolicyResponse(
            title = "개인정보 수집 및 이용 동의",
            content = "개인정보 수집 및 이용 동의 내용",
            version = "1.0.0"
        )
        return CommonResponse(personalInfoAgree)
    }
}