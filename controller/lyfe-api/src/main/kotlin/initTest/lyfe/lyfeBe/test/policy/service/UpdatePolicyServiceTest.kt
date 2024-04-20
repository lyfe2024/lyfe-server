package initTest.lyfe.lyfeBe.test.policy.service

import initTest.lyfe.lyfeBe.test.mock.FakePolicyRepository
import initTest.lyfe.lyfeBe.test.policy.PolicyFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyService

class UpdatePolicyServiceTest: BehaviorSpec({


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

    Given("정책 업데이트 서비스가 준비되었을 때") {

        val updatePolicy = PolicyFactory.createPolicyUpdate(policy.id)

        When("정책 업데이트 서비스를 처리할 때") {

            policyService.update(updatePolicy)
            val getPolicy = policyService.getPolicy(policy.policyType)

            Then("업데이트된 정책의 속성이 요청과 일치하는지 확인할 때") {
                getPolicy.content shouldBe updatePolicy.content
                getPolicy.title shouldBe updatePolicy.title
                getPolicy.version shouldBe updatePolicy.version
                getPolicy.policyType shouldBe updatePolicy.policyType
            }
        }
    }
})