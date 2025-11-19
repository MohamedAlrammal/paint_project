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
@DiscriminatorValue(value ="ellipse" )
public class Ellipse extends Shape {

    private double centerX;
    private double centerY;
    private double radiusX;
    private double radiusY;
    @Override
    public Shape clone() {
        Ellipse cloned= Clone.makeClone(this,Ellipse.class);
        return cloned;
    }
    @Override
    public Shape clone2() {
        Ellipse cloned= Clone.makeClone(this,Ellipse.class);
        cloned.setCenterY(this.centerY+3);
        cloned.setCenterX(this.centerX +3);
        return cloned;
    }
}
