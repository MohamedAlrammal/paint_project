package com.example.Painting.CommandPattern;

import com.example.Painting.Entities.Shape;
import com.example.Painting.Repository.CommandRepository;
import com.example.Painting.Repository.ShapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class Redo {
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    ShapeRepository shapeRepository;
    @Autowired
    ObjectMapper objectMapper;
    public  Pair<Shape,String>redo(){
        Command command=commandRepository.findTopByUndoTrueOrderByIdAsc().orElse(null);
        command.setUndo(false);
        commandRepository.save(command);
        Shape oldState = null;
        Shape newState = null;

        try {
            if (command.getOldState() != null)
                oldState = objectMapper.readValue(command.getOldState(), Shape.class);

            if (command.getNewState() != null)
                newState = objectMapper.readValue(command.getNewState(), Shape.class);

        } catch (Exception e) {
            throw new RuntimeException("Cannot map JSON to Shape");
        }






        if (     command.getType().equals(OpType.CHANGE_STROKECOLOR)
                ||command.getType().equals(OpType.CHANGE_FILLCOLOR)
                ||command.getType().equals(OpType.ROTATION)
                ||command.getType().equals(OpType.MOVE)
                ||command.getType().equals(OpType.CHANGE_STROKEWIDTH)     ) {


            shapeRepository.save(newState);
            Pair<Shape, String> p = Pair.of(newState,"changeTo");
            return  p;
        }





        else if(command.getType().equals(OpType.CREATE_SHAPE)){
            newState.setId(null);
            shapeRepository.save(newState);
            Pair<Shape, String> p = Pair.of(newState,"create");

            return  p;

        }





        else  if(command.getType().equals(OpType.DELETE_SHAPE)){
            shapeRepository.deleteById(oldState.getId());

            Pair<Shape, String> p = Pair.of(oldState,"delete");

            return p;
        }







        else {
            throw new RuntimeException("not have this operation ");
        }















    }

}
