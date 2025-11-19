package com.example.Painting.Factory;

import com.example.Painting.Dto.ShapeDto;
import com.example.Painting.Entities.*;
import org.springframework.stereotype.Service;

@Service
public class FactoryOfShapes {

  public static Shape createShape(ShapeDto dto){
      String type =dto.getType();
      if(type.equals("rect")){

return Rectangle.builder()
        .strokeColor(dto.getStrokeColor())
        .fillColor(dto.getFillColor())
        .strokeWidth(dto.getStrokeWidth())
        .rotation(dto.getRotation())
        .x(dto.getX())
        .y(dto.getY())
        .width(dto.getWidth())
        .height(dto.getHeight())
        .build();


     }

      else if(type.equals("square")){
          return Square.builder()
                  .strokeColor(dto.getStrokeColor())
                  .fillColor(dto.getFillColor())
                  .strokeWidth(dto.getStrokeWidth())
                  .rotation(dto.getRotation())
                  .x(dto.getX())
                  .y(dto.getY())
                  .side(dto.getSide())
                  .build();

      }

else if(type.equals("circle")){
    return Circle.builder()
            .strokeColor(dto.getStrokeColor())
            .fillColor(dto.getFillColor())
            .strokeWidth(dto.getStrokeWidth())
            .rotation(dto.getRotation())
            .centerX(dto.getCenterX())
            .centerY(dto.getCenterY())
            .radius(dto.getRadius())
            .build();


      }

else if(type.equals("ellipse")){
    return Ellipse.builder()
    .strokeColor(dto.getStrokeColor())
                  .fillColor(dto.getFillColor())
                  .strokeWidth(dto.getStrokeWidth())
                  .rotation(dto.getRotation())
            .centerX(dto.getCenterX())
            .centerY(dto.getCenterY())
            .radiusX(dto.getRadiusX())
            .radiusY(dto.getRadiusY())
            .build();
      }

else if(type.equals("line")){
    return Line.builder()
            .strokeColor(dto.getStrokeColor())
            .fillColor(dto.getFillColor())
            .strokeWidth(dto.getStrokeWidth())
            .rotation(dto.getRotation())
            .x1(dto.getX1())
            .y1(dto.getY1())
            .x2(dto.getX2())
            .y2(dto.getY2())
            .build();
      }
else if(type.equals("triangle")){
    return  Triangle.builder()
            .strokeColor(dto.getStrokeColor())
            .fillColor(dto.getFillColor())
            .strokeWidth(dto.getStrokeWidth())
            .rotation(dto.getRotation())
            .x1(dto.getX1())
            .y1(dto.getY1())
            .x2(dto.getX2())
            .y2(dto.getY2())
            .x3(dto.getX3())
            .y3(dto.getY3())
            .build();
      }

else {
    throw  new RuntimeException("unknown Shape");
      }

  }






}
