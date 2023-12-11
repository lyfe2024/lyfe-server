package initTest.policy.service

import initTest.mock.FakePolicyRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.policy.PolicyCreate
import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.PolicyType
import lyfe.lyfeBe.policy.PolicyUpdate

class BoardServiceCreateTest(
) : BehaviorSpec({

    val fakePolicyRepository = FakePolicyRepository()
    val service = PolicyService(
        fakePolicyRepository,

    )

    beforeContainer {


    }

    afterContainer {
        fakePolicyRepository.clear()
    }


    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val policyCreate = PolicyCreate(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )



        When("게시판 생성 요청을 처리할 때") {

            val newPolicy = service.create(policyCreate)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                newPolicy.title shouldBe policyCreate.title
                newPolicy.content shouldBe policyCreate.content
                newPolicy.version shouldBe policyCreate.version
                newPolicy.policyType shouldBe policyCreate.policyType
            }
        }
    }

    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val policyCreate = PolicyCreate(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        val newPolicy = service.create(policyCreate)


        val policyUpdate = PolicyUpdate(
            id = 1L,
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        When("게시판 생성 요청을 처리할 때") {

            val updatePolicy = newPolicy.update(policyUpdate)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                updatePolicy.id shouldBe policyUpdate.id
                updatePolicy.title shouldBe policyUpdate.title
                updatePolicy.content shouldBe policyUpdate.content
                updatePolicy.version shouldBe policyUpdate.version
                updatePolicy.policyType shouldBe policyUpdate.policyType
            }
        }
    }

    Given("게시판 생성 요청과 사용자, 주제가 준비되었을 때") {

        val policyCreate = PolicyCreate(
            title = "testTitle",
            content = "testContent",
            version = "testVersion",
            policyType = PolicyType.TERM
        )

        val newPolicy = service.create(policyCreate)




        When("게시판 생성 요청을 처리할 때") {

            service.getPolicy(PolicyType.TERM)

            Then("생성된 게시판의 속성이 요청과 일치하는지 확인할 때") {
                newPolicy.title shouldBe policyCreate.title
                newPolicy.content shouldBe policyCreate.content
                newPolicy.version shouldBe policyCreate.version
                newPolicy.policyType shouldBe policyCreate.policyType
            }
        }
    }
})