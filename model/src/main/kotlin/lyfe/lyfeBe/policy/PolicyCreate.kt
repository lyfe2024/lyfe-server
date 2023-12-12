package lyfe.lyfeBe.policy

data class PolicyCreate(
    val title: String,
    val content: String,
    val version: String,
    val policyType: PolicyType,
)