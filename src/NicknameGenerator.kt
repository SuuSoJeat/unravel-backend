interface NicknameGenerator {
    suspend fun generateOne(): String
}