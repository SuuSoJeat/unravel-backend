import org.koin.dsl.module
import services.NicknameGenerator
import services.NicknameGeneratorImpl

val applicationModule = module {
    single<NicknameGenerator> { NicknameGeneratorImpl() }
}