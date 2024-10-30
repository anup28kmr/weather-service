package com.ak.bkdprocess.repository;

import com.ak.common.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
  Optional<Place> findByName(String location);
}
