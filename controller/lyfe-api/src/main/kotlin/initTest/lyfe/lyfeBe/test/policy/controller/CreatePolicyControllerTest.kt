//package initTest.lyfe.lyfeBe.test.policy.controller
//
//import initTest.mock.TestContainer
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import lyfe.lyfeBe.policy.Policy
//import lyfe.lyfeBe.policy.PolicyType
//import lyfe.lyfeBe.web.policy.req.PolicySaveRequest
//
//
//class CreatePolicyControllerTest(
//) : BehaviorSpec({
//
//    val testContainer = TestContainer.build()
//
//
//
//    Given("정책 데이터가 저장 되고") {
//
//        val policySaveRequest = PolicySaveRequest(
//            title = "testTitle",
//            content = "testContent",
//            version = "testVersion",
//            policyType = PolicyType.TERM
//        )
//        testContainer.createPolicyController.createPolicy(policySaveRequest)
//
//
//        When("정책을 타입으로 조회했을 때 ") {
//
//            val newPolicy = testContainer
//                .getPolicyController
//                .getPolicy(PolicyType.TERM).result
//
//            Then("해당 타입에 해당되는 정책이 조회가 된다 ") { //FIXIME 좀더 정책적으로 기획디면 추후 수정
//                newPolicy.title shouldBe "testTitle"                   // 예를들면 타입은 유니크?
//                newPolicy.content shouldBe "testContent"
//                newPolicy.version shouldBe "testVersion"
//                newPolicy.policyType shouldBe PolicyType.TERM
//            }
//        }
//    }
