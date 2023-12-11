package initTest.mock

import lyfe.lyfeBe.policy.PolicyService
import lyfe.lyfeBe.policy.out.PolicyPort
import lyfe.lyfeBe.web.policy.CreatePolicyController
import lyfe.lyfeBe.web.policy.GetPolicyController
import lyfe.lyfeBe.web.policy.UpdatePolicyController

class TestContainer(
    var getPolicyController: GetPolicyController,
    var createPolicyController: CreatePolicyController,
    var updatePolicyController: UpdatePolicyController,
    var service: PolicyService,
    var policyRepository: PolicyPort

) {

    companion object {
        fun build(): TestContainer {
            val repository = FakePolicyRepository()


            val service = PolicyService(
                repository
            )

            return TestContainer(
                GetPolicyController(service),
                CreatePolicyController(service),
                UpdatePolicyController(service),
                service,
                repository
            )
        }
    }
}


