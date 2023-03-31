package guru.springframework.bootstrap;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private RecipeRepository recipeRepository;

    public DataLoader(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Recipe recipeGuatamole = new Recipe();
        recipeGuatamole.setDescription("Perfect Guacamole");
        recipeRepository.save(recipeGuatamole);

        Recipe recipeChicken = new Recipe();
        recipeChicken.setDescription("Spicy Grilled Chicken");
        recipeRepository.save(recipeChicken);


    }
}
