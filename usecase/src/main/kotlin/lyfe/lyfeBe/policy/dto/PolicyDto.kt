package lyfe.lyfeBe.policy.dto

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.policy.Policy

data class PolicyDto(
        val title : String,
        val content : String,
        val version : String
) {
    companion object {
        fun toDto(policy: Policy): PolicyDto {
            return PolicyDto(
                    title = policy.title,
                    content = policy.content,
                    version = policy.version
            )
        }
    }
}