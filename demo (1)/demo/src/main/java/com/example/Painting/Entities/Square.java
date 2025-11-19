package com.example.Painting.Entities;

import com.example.Painting.Cloning.Clone;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@DiscriminatorValue(value = "square")
@SuperBuilder
public class Square extends Shape {
    double x;
    double y;
    double side;
    @Override
    public Shape clone() {
        Square cloned= Clone.makeClone(this,Square.class);
        return cloned;
    }
    public Shape clone2() {
        Square cloned= Clone.makeClone(this,Square.class);
        cloned.setX(this.x+3);
        cloned.setY(this.y+3);
        return cloned;
    }

}
