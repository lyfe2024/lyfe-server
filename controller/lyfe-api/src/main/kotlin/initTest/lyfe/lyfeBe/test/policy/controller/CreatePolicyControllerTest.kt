package initTest.lyfe.lyfeBe.test.policy.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest


class CreatePolicyControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()

    Given("정책 생성 요청이 준비되었을 때") {

        val policySaveRequest = PolicySaveRequest(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        val policyId = testContainer.policyController.createPolicy(policySaveRequest)

        val policy = testContainer.policyController.getPolicy(
            policyId.result.policyType.toString().lowercase()
        )

        When("생성된 정책의 값이 일치해야 한다") {

            Then("생성된 정책과 조회된 정책의 값 검증") {
                policy.result.title shouldBe policySaveRequest.title
                policy.result.content shouldBe policySaveRequest.content
                policy.result.version shouldBe policySaveRequest.version
                policy.result.policyType shouldBe policySaveRequest.policyType
            }

        }
    }
})
