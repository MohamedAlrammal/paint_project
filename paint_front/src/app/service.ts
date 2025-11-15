import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Service {
     private static  urlStages:string = "api/stages/";
     private static  urlShapes:string = "api/shapes/";
    public  static async loadFullStage(stageName:string){
      try{  
        const response  = await fetch(this.urlStages + stageName);
        
        if (!response.ok) {
        throw new Error(`it seems that the image is not found or its name is incorrect, status: ${response.status}`);
        } 
        
        const data = await response.json();

        return data;
      }catch(error){
        console.error("error in loading the image" + error);
      }
    }

    public static async saveFullStage(stageJson:string){
      try{  
        const response  = await fetch(this.urlStages, {
          method:"POST",
          headers:{ "Content_Type":'application/json'}, 
          body: stageJson});
        
        if (!response.ok) {
        throw new Error(`Server can't save the image., status: ${response.status}`);
        } 
        
        const data = await response.json();
        console.log("returned acknowledgement after saving" + data);
      }catch(error){
        console.error("error in saving the image" + error);
      }
    }

    public static async saveCreatedShape(shapeJson:string){
      try{  
        const response  = await fetch(this.urlShapes, {
          method:"POST",
          headers:{ "Content_Type":'application/json'}, 
          body: shapeJson}
        );
        
        if (!response.ok) {
        throw new Error(`Server can't save(send) the data of the shape., status: ${response.status}`);
        } 
        
        const data = await response.json();
        console.log("returned acknowledgement after saving" + data);
      }catch(error){
        console.error("error in saving the shape" + error);
      }
    }

    public static async updateShape(shapeJson:string){
      try{  
        const response  = await fetch(this.urlShapes + JSON.parse(shapeJson).id, {
          method:"PUT",
          headers:{ "Content_Type":'application/json'}, 
          body: shapeJson}
        );
        
        if (!response.ok) {
        throw new Error(`Server can't save(send) the data of the shape., status: ${response.status}`);
        } 
        
        const data = await response.json();
        console.log("returned acknowledgement after saving" + data);
      }catch(error){
        console.error("error in saving the shape" + error);
      }

    }

    public static deleteShape(){

    }

    public static copyShape(){

    }
}
