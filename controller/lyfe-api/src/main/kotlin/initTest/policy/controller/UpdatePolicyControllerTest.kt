package initTest.policy.controller

import initTest.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.web.policy.req.PolicyUpdateReq


class UpdatePolicyControllerTest(
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



    Given("정책 업데이트 요청이 준비되었을 때") {

        val policyUpdateReq = PolicyUpdateReq(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )


        When("정책 생성 요청을 처리할 때") {
            val updatePolicy = testContainer
                .updatePolicyController
                .updatePolicy(1L,policyUpdateReq).result


            Then("생성된 정책의 속성이 요청과 일치하는지 확인할 때") {
                updatePolicy.title shouldBe policyUpdateReq.title
                updatePolicy.content shouldBe policyUpdateReq.content
                updatePolicy.version shouldBe policyUpdateReq.version
                updatePolicy.policyType shouldBe policyUpdateReq.policyType
            }
        }
    }
})