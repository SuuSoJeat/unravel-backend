import org.koin.dsl.module

val applicationModule = module {
    single<NicknameGenerator> { NicknameGeneratorImpl() }
}
