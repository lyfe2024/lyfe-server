package initTest.lyfe.lyfeBe.test.policy.service

import initTest.lyfe.lyfeBe.test.mock.FakePolicyRepository
import initTest.lyfe.lyfeBe.test.policy.PolicyFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyService

class GetPolicyServiceTest: BehaviorSpec({

    val fakePolicyRepository = FakePolicyRepository()
    val policyService = PolicyService(
        fakePolicyRepository
    )

    lateinit var policy: Policy

    beforeContainer {
        policy = PolicyFactory.createTestPolicy()
        fakePolicyRepository.create(policy)
    }

    afterContainer {
        fakePolicyRepository.clear()
    }

    Given("정책 조회 서비스가 준비되었을 때") {

        When("정책 조회 서비스를 처리할 때") {

            val getPolicy = policyService.getPolicy(policy.policyType)

            Then("정책의 속성이 요청과 일치하는지 확인할 때") {
                getPolicy.content shouldBe policy.content
                getPolicy.title shouldBe policy.title
                getPolicy.version shouldBe policy.version
                getPolicy.policyType shouldBe policy.policyType
            }
        }
    }
})