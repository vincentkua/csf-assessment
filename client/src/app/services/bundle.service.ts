import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, map } from 'rxjs';
import { archive } from '../models/models';

const URL : string = "http://localhost:8080"

@Injectable({
  providedIn: 'root'
})
export class BundleService {

  constructor(private http:HttpClient) { }

  getBundleById(bundleId:string){
    const GETIDURL = `${URL}/bundle/${bundleId}`

    return lastValueFrom(
      this.http.get<any>(GETIDURL)
      .pipe(
        map(v=>{
          return v as archive          
        })
      ) 
    )
  }

  getAllBundles(){
    const GETIDURL = `${URL}/bundles`
    return lastValueFrom(
      this.http.get<any>(GETIDURL)
    )
  }


}
