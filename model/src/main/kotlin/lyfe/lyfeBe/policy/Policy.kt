package lyfe.lyfeBe.policy

data class Policy(
    val id: Long = 0,
    val title: String,
    val content: String,
    val version: String,
    val policyType: PolicyType,
) {
    fun update(policyUpdate: PolicyUpdate) =
        Policy(
            id = policyUpdate.id,
            title = policyUpdate.title,
            content = policyUpdate.content,
            version = policyUpdate.version,
            policyType = policyUpdate.policyType
        )


    companion object {
        fun from(policyCreate: PolicyCreate): Policy {
            return Policy(
                title = policyCreate.title,
                content = policyCreate.content,
                version = policyCreate.version,
                policyType = policyCreate.policyType
            )
        }
    }
}
