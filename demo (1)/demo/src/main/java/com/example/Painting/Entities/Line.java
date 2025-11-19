package com.example.Painting.Entities;

import com.example.Painting.Cloning.Clone;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value = "line")
public class Line extends Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    @Override
    public Shape clone() {
        Line cloned= Clone.makeClone(this,Line.class);
        return cloned;
    }
    @Override
    public Shape clone2() {
        Line cloned= Clone.makeClone(this,Line.class);
        return cloned;
    }
}
