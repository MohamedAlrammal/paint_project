package com.example.Painting.Service;

import com.example.Painting.CommandPattern.Command;
import com.example.Painting.CommandPattern.OpType;
import com.example.Painting.CommandPattern.Redo;
import com.example.Painting.CommandPattern.Undo;
import com.example.Painting.Dto.ShapeDto;
import com.example.Painting.Entities.*;
import com.example.Painting.Factory.FactoryOfShapes;
import com.example.Painting.Repository.CommandRepository;
import com.example.Painting.Repository.ShapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeService {
    @Autowired
    ShapeRepository shapeRepository;
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Undo undo;
    @Autowired
    Redo redo;
//    there is some problems in classes in diffrent package
    public Shape createShape(ShapeDto dto){
        Shape shape_created= FactoryOfShapes.createShape(dto);
      shape_created=  shapeRepository.save(shape_created);
        Command command;
        try {
         command=
                   Command.builder().type(OpType.CREATE_SHAPE).
                    oldState(null).
                    newState(objectMapper.writeValueAsString(shape_created)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}

        commandRepository.save(command);
        return shape_created;
    }

    public Shape changeFillColor(String color,Long shapeId){
        Shape shape=shapeRepository.findById(shapeId).orElse(null);
        Shape oldState=shape.clone();
        shape.setFillColor(color);
        Shape newState=shape;
        newState= shapeRepository.save(newState);
         Command command;
         try {
             command=
                     Command.builder().type(OpType.CHANGE_FILLCOLOR).
                             oldState(objectMapper.writeValueAsString(oldState)).
                             newState(objectMapper.writeValueAsString(newState)).undo(false).build();

         }catch (Exception e){ throw  new RuntimeException("can not mapper");}

return  newState;

    }


    public Shape changeStrokeColor(String color,Long shapeId){
        Shape shape=shapeRepository.findById(shapeId).orElse(null);
        Shape oldState=shape.clone();
        shape.setStrokeColor(color);
        Shape newState=shape;
        newState = shapeRepository.save(newState);
        Command command;
        try {
            command=
                    Command.builder().type(OpType.CHANGE_STROKECOLOR).
                            oldState(objectMapper.writeValueAsString(oldState)).
                            newState(objectMapper.writeValueAsString(newState)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}

        return  newState;

    }

    public Shape changeStrokeWidth(double width,Long shapeId){
        Shape shape=shapeRepository.findById(shapeId).orElse(null);
        Shape oldState=shape.clone();
        shape.setStrokeWidth(width);
        Shape newState=shape;
        newState= shapeRepository.save(newState);
        Command command;
        try {
            command=
                    Command.builder().type(OpType.CHANGE_STROKEWIDTH).
                            oldState(objectMapper.writeValueAsString(oldState)).
                            newState(objectMapper.writeValueAsString(newState)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}

        return  newState;

    }

    public Shape changeRotation(double angle,Long shapeId){
        Shape shape=shapeRepository.findById(shapeId).orElse(null);
        Shape oldState=shape.clone();
        shape.setRotation(angle);
        Shape newState=shape;
        newState= shapeRepository.save(newState);
        Command command;
        try {
            command=
                    Command.builder().type(OpType.ROTATION).
                            oldState(objectMapper.writeValueAsString(oldState)).
                            newState(objectMapper.writeValueAsString(newState)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}

        return  newState;

    }


    public Shape resizeAndMove(ShapeDto dto){
        Shape shape=shapeRepository.findById(dto.getId()).orElse(null);
        Shape oldState=shape.clone();
        Shape newState =shape;
        if(dto.getType().equals("Rectangle")){
            ((Rectangle) shape).setWidth(dto.getWidth());
            ((Rectangle) shape).setHeight(dto.getHeight());
            ((Rectangle) shape).setX(dto.getX());
            ((Rectangle) shape).setY(dto.getY());
            newState =shape;

        }
        else  if(dto.getType().equals("Square")){
            ((Square) shape).setSide(dto.getSide());
            ((Square) shape).setX(dto.getX());
            ((Square) shape).setY(dto.getY());
newState =shape;

        }

        else if( dto.getType().equals("Ellipse")){
            ((Ellipse) shape).setRadiusX(dto.getRadiusX());
            ((Ellipse) shape).setRadiusY(dto.getRadiusY());
            ((Ellipse) shape).setCenterX(dto.getCenterX());
            ((Ellipse) shape).setCenterY(dto.getCenterY());
            newState =shape;

        }
        else if(dto.getType().equals("Circle")){
            ((Circle) shape).setRadius(dto.getRadius());
            ((Circle) shape).setCenterX(dto.getCenterX());
            ((Circle) shape).setCenterY(dto.getCenterY());
        }


        else if(dto.getType().equals("Line")){
            ((Line)shape).setX1(dto.getX1());
            ((Line)shape).setY1(dto.getY1());
            ((Line)shape).setX2(dto.getX2());
            ((Line)shape).setY2(dto.getY2());


        }
        else  if(dto.getType().equals("Triangle")){

            ((Triangle)shape).setX1(dto.getX1());
            ((Triangle)shape).setY1(dto.getY1());
            ((Triangle)shape).setX2(dto.getX2());
            ((Triangle)shape).setY2(dto.getY2());
            ((Triangle)shape).setX3(dto.getX3());
            ((Triangle)shape).setY3(dto.getY3());


        }
        Command command;
        try {
            command=
                    Command.builder().type(OpType.MOVE).
                            oldState(objectMapper.writeValueAsString(oldState)).
                            newState(objectMapper.writeValueAsString(newState)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}


        return newState;
    }


    public  Shape copy(Long shapeId){
        Shape shape=shapeRepository.findById(shapeId).orElse(null);
        Shape oldState=shape;
        Shape clonedShape =shape.clone2();
        clonedShape.setId(null);

        clonedShape=shapeRepository.save(clonedShape);

Command command;

        try {
            command=
                    Command.builder().type(OpType.COPY).
                            oldState(objectMapper.writeValueAsString(oldState)).
                            newState(objectMapper.writeValueAsString(clonedShape)).undo(false).build();

        }catch (Exception e){ throw  new RuntimeException("can not mapper");}

        return clonedShape;


    }

public Pair<Shape,String> undo(){
     Pair <Shape,String>  p =  undo.undo();
    return p;
}



    public Pair<Shape,String> redo(){
        Pair <Shape,String>  p =  redo.redo();
        return p;
    }

public ByteArrayResource exportJson() {
    List<Shape> shapes = shapeRepository.findAll();
    String json;
    try {
        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(shapes);

    } catch (Exception e) {
        throw new RuntimeException("no ");
    }
    ByteArrayResource resource=new ByteArrayResource(json.getBytes());
    return resource;
}




}
