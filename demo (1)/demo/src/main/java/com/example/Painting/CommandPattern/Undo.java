package com.example.Painting.CommandPattern;

import com.example.Painting.Entities.Shape;
import com.example.Painting.Repository.CommandRepository;
import com.example.Painting.Repository.ShapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class Undo {
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    ShapeRepository shapeRepository;
    @Autowired
    ObjectMapper objectMapper;

    public Pair<Shape,String> undo(){

Command command=commandRepository.findTopByUndoFalseOrderByIdDesc().orElse(null);

if(command==null){
    throw  new RuntimeException("no further undo");
}
command.setUndo(true);
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


shapeRepository.save(oldState);
    Pair<Shape, String> p = Pair.of(oldState,"changeTo");
return  p;
}





else if(command.getType().equals(OpType.CREATE_SHAPE)){
    shapeRepository.deleteById(newState.getId());
    Pair<Shape, String> p = Pair.of(newState,"delete");

    return  p;

}





else  if(command.getType().equals(OpType.DELETE_SHAPE)){
    oldState.setId(null);
    shapeRepository.save(oldState);
    Pair<Shape, String> p = Pair.of(oldState,"create");

    return p;
}







else {
    throw new RuntimeException("not have this operation ");
}











    }
}
