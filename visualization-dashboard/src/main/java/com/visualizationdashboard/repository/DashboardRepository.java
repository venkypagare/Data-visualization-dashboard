package com.visualizationdashboard.repository;

import com.visualizationdashboard.entity.DashboardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardEntity, Long> {
    Page<DashboardEntity> findAllByEndYearAndTopicAndSectorAndRegionAndPestleAndSourceAndSwotAndCountryAndCity(
            Integer endYear, String topic, String sector, String region,
            String pestle, String source, String swot, String country, String city,
            Pageable pageable);
}
