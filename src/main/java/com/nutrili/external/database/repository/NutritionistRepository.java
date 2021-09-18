package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
    @Query("select n from Nutritionist n join n.addressId a where a.city LIKE :city order by n.score")
    List<Nutritionist> findByCity(@Param("city") String city);

    @Query("select n from Nutritionist n where n.name LIKE :name order by n.score")
    List<Nutritionist> findByName(@Param("name") String name);

}
