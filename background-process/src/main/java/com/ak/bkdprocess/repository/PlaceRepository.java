package com.ak.bkdprocess.repository;

import com.ak.common.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
  List<Place> findByName(String location);
}
