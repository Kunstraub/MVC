package guru.springframework.sfgdi.repositories;

public class EnglishGreetingRepostitoryImpl implements EnglishGreetingRepository {
    @Override
    public String getRepoEN() {
        return "get English Greet from Database!";
    }
}
