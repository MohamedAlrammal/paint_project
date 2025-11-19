package com.example.Painting.Repository;

import com.example.Painting.Entities.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeRepository extends JpaRepository<Shape,Long> {

}
