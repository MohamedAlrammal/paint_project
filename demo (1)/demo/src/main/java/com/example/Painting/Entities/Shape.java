package com.example.Painting.Entities;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//what about square
@DiscriminatorColumn(name="shape_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,property = "type")
public  abstract class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String strokeColor;
    private String fillColor;
    private double strokeWidth;
    private double rotation;
    public abstract Shape clone();
    public abstract Shape clone2();

//public Shape(String strokeColor,String fillColor,double strokeWidth,double rotation){
//    this.strokeColor=strokeColor;
//    this.fillColor=fillColor;
//    this.strokeWidth=strokeWidth;
//    this.rotation=rotation;
//}









}
