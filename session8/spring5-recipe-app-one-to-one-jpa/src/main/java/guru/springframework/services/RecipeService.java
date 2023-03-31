package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

  private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getRecipes(){
        List<Recipe> recipeList = new ArrayList<>();
        Iterable<Recipe> recipes = recipeRepository.findAll();
        recipes.forEach(recipeList::add);
        return recipeList;
    }
}