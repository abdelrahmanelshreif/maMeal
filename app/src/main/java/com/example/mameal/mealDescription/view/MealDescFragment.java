package com.example.mameal.mealDescription.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.mameal.R;
import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.model.RecipeTab;
import com.example.mameal.mealDescription.presenter.MealDescriptionPresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class MealDescFragment extends Fragment implements RecipeDetailsContract.View {
    private Chip chipIngredients, chipProcedure;

    private ChipGroup chipGroup;
    private FragmentContainerView fragmentContainerView;
    MealDescriptionPresenter mealDescriptionPresenter;


    public MealDescFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mealDescriptionPresenter = new MealDescriptionPresenter(MaMealRepository.getInstance(new MaMealRemoteDataSource()), new IngredientWithMeasure(), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_desc, container, false);
        chipIngredients = view.findViewById(R.id.ingredientChip);
        chipProcedure = view.findViewById(R.id.procedureChip);
        fragmentContainerView = view.findViewById(R.id.ingredientProcedureFragmentView);
        chipGroup = view.findViewById(R.id.mealDiscChipGroup);

        if (savedInstanceState == null) {
            mealDescriptionPresenter.onChipSelected(RecipeTab.INGREDIENTS);
            chipIngredients.setChecked(true);
        }

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == chipIngredients.getId()) {
                mealDescriptionPresenter.onChipSelected(RecipeTab.INGREDIENTS);
            } else if (checkedId == chipProcedure.getId()) {
                mealDescriptionPresenter.onChipSelected(RecipeTab.PROCEDURE);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (savedInstanceState == null) {
            IngredientFragment ingredientFragment =IngredientFragment.newInstance(mealDescriptionPresenter);
            showFragment(ingredientFragment);

        }

    }

    @Override
    public void showFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.ingredientProcedureFragmentView, fragment)
                .commit();
    }

    @Override
    public void showIngredients(List<IngredientWithMeasure> ingredientList) {

    }


}