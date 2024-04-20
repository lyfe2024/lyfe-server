package initTest.lyfe.lyfeBe.test.policy.service

import initTest.lyfe.lyfeBe.test.mock.FakePolicyRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType

class CreatePolicyServiceTest: BehaviorSpec({

    val fakePolicyRepository = FakePolicyRepository()
    val policyService = PolicyService(
        fakePolicyRepository
    )

    Given("정책 생성 서비스가 준비되었을 때") {

        val policyCreate = PolicyCreate(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.PERSONAL_INFO_AGREEMENT
        )


        When("정책 생성을 처리할 때") {
            policyService.create(policyCreate)
            val createPolicy = policyService.getPolicy(PolicyType.PERSONAL_INFO_AGREEMENT)

            Then("생성된 정책의 속성이 요청과 일치하는지 확인할 때") {
                createPolicy.content shouldBe policyCreate.content
                createPolicy.title shouldBe policyCreate.title
                createPolicy.version shouldBe policyCreate.version
                createPolicy.policyType shouldBe policyCreate.policyType
            }
        }
    }
})