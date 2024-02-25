package com.example.repo;

import com.example.entity.Asset;
import com.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByName(String name);

    Optional<Asset> findByBatchId(String batchId);

    List<Asset> findByCategory(Category category);



}
