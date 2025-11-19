package com.example.Painting.Dto;

import lombok.Data;

@Data
public class ShapeDto {
    private Long id;
    private   String type;
    private String strokeColor;
    private String fillColor;
    private double strokeWidth;
    private double rotation;
    private double x1; private double y1;
    private double x2; private double y2;
    private double x3; private double y3;
    double x;
    double y;
    double width;
    double height;
    double side;
//    private double x1l;
//    private double y1l;
//    private double x2l;
//    private double y2l;
//    private double centerXell;
//    private double centerYell;
    private double radiusX;
    private double radiusY;
    double centerX;
    double centerY;
    double radius;

}