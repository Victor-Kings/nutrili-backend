package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NutritionistRepository extends JpaRepository<Nutritionist, UUID> {
    @Query("select n from Nutritionist n join n.officeId a where upper(a.city) LIKE upper(:city) and a.city is not null order by n.score")
    List<Nutritionist> findByCity(@Param("city") String city);

    @Query("select n from Nutritionist n where upper(n.name) LIKE upper(:name) order by n.score")
    List<Nutritionist> findByName(@Param("name") String name);

}
