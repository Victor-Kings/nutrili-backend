package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.entity.WeightHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeightHistoryRepository extends JpaRepository<WeightHistory, UUID> {
}
