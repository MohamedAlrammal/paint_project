package com.example.Painting.Entities;

import com.example.Painting.Cloning.Clone;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value = "triangle")
public class Triangle extends Shape {
    private double x1; private double y1;
    private double x2; private double y2;
    private double x3; private double y3;
    @Override
    public Shape clone() {
        Triangle cloned= Clone.makeClone(this,Triangle.class);
        return cloned;
    }
    @Override
    public Shape clone2() {
        Triangle cloned= Clone.makeClone(this,Triangle.class);
        return cloned;
    }

}
