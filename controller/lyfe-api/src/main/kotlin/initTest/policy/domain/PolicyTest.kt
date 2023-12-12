package initTest.policy.domain

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.Policy
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate


@OptIn(ExperimentalKotest::class)
class PolicyTest(
) : BehaviorSpec({

    Given("PolicyCreate 객체가 초기화되었을 때") {

        val policyCreate = PolicyCreate(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        When("PolicyCreate 객체를 사용하여 새 Policy 객체를 생성했을 때") {
            val policy = Policy.from(policyCreate)

            Then("생성된 Policy 객체의 속성이 policyCreate와 일치") {
                policy.title shouldBe policyCreate.title
                policy.content shouldBe policyCreate.content
                policy.version shouldBe policyCreate.version
                policy.policyType shouldBe policyCreate.policyType
            }
        }
    }


        Given("Policy객체를 생성하고 policyUpdate 생성 했을때  때") {

            val policyCreate = PolicyCreate(
                title = "testTitle",
                content = "testContent",
                version = "testVersion",
                policyType = PolicyType.TERM
            )

            val policy = Policy.from(policyCreate)

            val policyUpdate = PolicyUpdate(
                id = 1L,
                title = "testTitle",
                content = "testContent",
                version = "testVersion",
                policyType = PolicyType.TERM
            )

            When("policyUpdate를 사용하여 Policy객체를 업데이트 했을때") {
                val updatePolicy = policy.update(policyUpdate)

                Then("업데이트된 Policy객체의 속성이 policyUpdate와 일치") {
                    policy.title shouldBe updatePolicy.title
                    policy.content shouldBe updatePolicy.content
                    policy.version shouldBe updatePolicy.version
                    policy.policyType shouldBe updatePolicy.policyType
                }
            }

        }
})