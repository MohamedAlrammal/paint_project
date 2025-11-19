package com.example.Painting.Controller;

import com.example.Painting.Dto.ShapeDto;
import com.example.Painting.Entities.Shape;
import com.example.Painting.Service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shape/")
@CrossOrigin("*")
public class ShapeController {
    @Autowired
    ShapeService shapeService;
@PostMapping("/create")
    public Shape createShape(@RequestBody ShapeDto dto){
      return   shapeService.createShape(dto);

    }
    @PostMapping("/changeFillColor/{color}/{shapeId}")
    public Shape changeFillColor(@PathVariable String color,@PathVariable Long shapeId){
     return    shapeService.changeFillColor(color,shapeId);
    }
    @PostMapping("/changeStrokeColor/{color}/{shapeId}")

    public Shape changeStrokeColor(@PathVariable String color, @PathVariable Long shapeId){
        return shapeService.changeStrokeColor(color,shapeId);
    }

    @PostMapping("/changeStrokeWidth/{width}/{shapeId}")
    public Shape changeStrokeWidth( @PathVariable double width, @PathVariable Long shapeId){
        return shapeService.changeStrokeWidth(width,shapeId);
    }
@PostMapping("/changeRotation")
    public Shape changeRotation( @RequestParam double angle, @RequestParam Long shapeId){
        return shapeService.changeRotation(angle,shapeId);
    }
    @PostMapping("/resizeAndMove")

    public Shape resizeAndMove(@RequestBody  ShapeDto dto){
    return shapeService.resizeAndMove(dto);

    }
    @PostMapping("/copy")
    public  Shape copy(  @RequestParam Long shapeId){
        return shapeService.copy(shapeId);
    }

    @GetMapping("/undo")
    public Pair<Shape,String> undo( ){
        return shapeService.undo();
    }

    @GetMapping("/redo")
    public Pair<Shape,String> redo( ){
        return shapeService.redo();
    }

    @GetMapping("/exportJson")
    public ResponseEntity<Resource> exportJson( ){
        ByteArrayResource resource = shapeService.exportJson();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=shapes.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }







}
