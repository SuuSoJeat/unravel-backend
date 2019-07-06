class NicknameGeneratorImpl : NicknameGenerator {

    private val dummyNicknames = listOf("Iron Man", "Spider Man", "Zhang Xuan", "Zhou Weiqing")

    override suspend fun generateOne(): String {
        return dummyNicknames.random()
    }
}