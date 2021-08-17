package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

}
