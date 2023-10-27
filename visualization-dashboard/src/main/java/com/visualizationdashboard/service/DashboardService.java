package com.visualizationdashboard.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.visualizationdashboard.entity.DashboardEntity;
import com.visualizationdashboard.repository.DashboardRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public void transferCSVDataToDB(MultipartFile file) throws IOException {
        List<DashboardEntity> entities = readCSVFile(file);
        dashboardRepository.saveAll(entities);
    }

    @SneakyThrows
    private List<DashboardEntity> readCSVFile(MultipartFile file) throws IOException {
        List<DashboardEntity> entities = new ArrayList<>();

        try (Reader reader = new StringReader(new String(file.getBytes()));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                DashboardEntity entity = new DashboardEntity();
                entity.setEndYear(Integer.parseInt(nextRecord[0]));
                entity.setCityLng(Double.parseDouble(nextRecord[1]));
                entity.setCityLat(Double.parseDouble(nextRecord[2]));
                entity.setIntensity(Integer.parseInt(nextRecord[3]));
                entity.setSector(nextRecord[4]);
                entity.setTopic(nextRecord[5]);
                entity.setInsight(nextRecord[6]);
                entity.setSwot(nextRecord[7]);
                entity.setUrl(nextRecord[8]);
                entity.setRegion(nextRecord[9]);
                entity.setStartYear(Integer.parseInt(nextRecord[10]));
                entity.setImpact(nextRecord[11]);
                entity.setAdded(nextRecord[12]);
                entity.setPublished(nextRecord[13]);
                entity.setCity(nextRecord[14]);
                entity.setCountry(nextRecord[15]);
                entity.setRelevance(Integer.parseInt(nextRecord[16]));
                entity.setPestle(nextRecord[17]);
                entity.setSource(nextRecord[18]);
                entity.setTitle(nextRecord[19]);
                entity.setLikelihood(nextRecord[20]);

                entities.add(entity);
            }
        }

        return entities;
    }

    // Existing method for data retrieval
    public Page<DashboardEntity> getDataWithFilters(
            Integer endYear, String topic, String sector, String region,
            String pestle, String source, String swot, String country, String city,
            Pageable pageable) {
        return dashboardRepository.findAllByEndYearAndTopicAndSectorAndRegionAndPestleAndSourceAndSwotAndCountryAndCity(
                endYear, topic, sector, region, pestle, source, swot, country, city, pageable);
    }
}
