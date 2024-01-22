package lyfe.lyfeBe.auth.dto.apple

/**
 * https://developer.apple.com/documentation/sign_in_with_apple/fetch_apple_s_public_key_for_verifying_token_signature 참고
 */
data class ApplePublicKeysResult(
    // 공개키 목록
    val keys: List<ApplePublicKey>
) {
    fun getMatchesKey(alg: String?, kid: String?): ApplePublicKey {
        return keys
            .firstOrNull { k -> k.alg == alg && k.kid == kid }
            ?: throw IllegalArgumentException("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.")
    }
}

data class ApplePublicKey(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
)