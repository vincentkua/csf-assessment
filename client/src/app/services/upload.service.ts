import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { uploadrequest } from '../models/models';

const URL : string = "http://localhost:8080/api/fileupload"

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http:HttpClient) { }



  uploadArchive(newrequest : uploadrequest){
    const formData = new FormData();
    formData.append("file", newrequest.selectedfile);
    formData.append("name", newrequest.name);
    formData.append("title", newrequest.title);
    formData.append("comments", newrequest.comments);
    const upload$ = this.http.post(URL, formData);
    upload$.subscribe({
      next:v => {
        console.log(v)
      },
      error: err =>{
        console.error("error : " + err)
      },
      complete: () => {
        console.warn("completed...")        
      }
    });
  }
}
