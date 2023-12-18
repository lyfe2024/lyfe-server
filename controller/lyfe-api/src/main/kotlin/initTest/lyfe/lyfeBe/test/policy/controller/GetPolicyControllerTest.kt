package initTest.lyfe.lyfeBe.test.policy.controller

import initTest.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.web.policy.req.PolicySaveRequest
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq


class GetPolicyControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()





    Given("조회할 정책을 저장하고") {

        val policySaveRequest = PolicySaveRequest(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )
        testContainer.createPolicyController.createPolicy(policySaveRequest)


        When("저장된 정책을 타입으로 조회할 때 ") {
            val policy = testContainer.getPolicyController
                .getPolicy(PolicyType.TERM).result


            Then("생성된 정책과 조회된 정책의 값 검증") {
                policy.title shouldBe policySaveRequest.title
                policy.content shouldBe policySaveRequest.content
                policy.version shouldBe policySaveRequest.version
                policy.policyType shouldBe policySaveRequest.policyType
            }
        }
    }
})