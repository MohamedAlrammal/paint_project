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
@DiscriminatorValue(value = "circle")
public class Circle  extends Shape {
    double centerX;
    double centerY;
    double radius;

    @Override
    public Shape clone() {
        Circle clonedCircle= Clone.makeClone(this,Circle.class);
        return clonedCircle;
    }
    @Override
    public Shape clone2() {
        Circle clonedCircle= Clone.makeClone(this,Circle.class);
        clonedCircle.setCenterX(this.centerX+3);
        clonedCircle.setCenterY(this.centerY+3);
        return clonedCircle;
    }
}
