package lyfe.lyfeBe.policy

data class PolicyUpdate(
    val id: Long,
    val title: String,
    val content: String,
    val version: String,
    val policyType: PolicyType,
)