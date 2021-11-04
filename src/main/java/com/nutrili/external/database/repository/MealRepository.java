package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {
}
