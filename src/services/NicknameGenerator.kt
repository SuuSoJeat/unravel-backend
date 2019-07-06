package services

interface NicknameGenerator {
    suspend fun generateOne(): String
}