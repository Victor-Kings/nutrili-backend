package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Dataset;
import com.nutrili.external.database.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DatasetRepository extends JpaRepository<Dataset, UUID> {

    @Query("select d from Dataset d where lower(:englishName) = lower(d.englishName)")
    Optional<Dataset> findByEnglishName(@Param("englishName") String englishName);

    @Query("select d from Dataset d where lower(:Name) = lower(d.name)")
    Optional<Dataset> findByName(@Param("Name") String Name);

    @Query("select d from Dataset d where lower(:Name) LIKE lower(d.name)")
    Optional<Dataset> findByNameLike(@Param("Name") String Name);

}
