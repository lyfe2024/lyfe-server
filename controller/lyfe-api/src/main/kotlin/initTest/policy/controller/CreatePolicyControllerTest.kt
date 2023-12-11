package initTest.policy.controller

import initTest.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest


class CreatePolicyControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()


    beforeContainer {

        val policy = Policy(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        testContainer.policyRepository.create(policy)

    }



    Given("정책 생성 요청이 준비되었을 때") {

        val policySaveRequest = PolicySaveRequest(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        When("정책 생성 요청을 처리할 때") {

            val newPolicy = testContainer
                .createPolicyController
                .createPolicy(policySaveRequest)
                .result

            Then("생성된 정책의 속성이 요청과 일치하는지 확인할 때") {
                newPolicy.title shouldBe policySaveRequest.title
                newPolicy.content shouldBe policySaveRequest.content
                newPolicy.version shouldBe policySaveRequest.version
                newPolicy.policyType shouldBe policySaveRequest.policyType
            }
        }
    }
})