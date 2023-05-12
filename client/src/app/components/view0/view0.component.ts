import { Component, OnInit } from '@angular/core';
import { archive } from 'src/app/models/models';
import { BundleService } from 'src/app/services/bundle.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  bundles !: archive[]
  
  constructor(private bundleSvc : BundleService){}

  ngOnInit(): void {
      //Call Bundle Service and get all the bundles....
      this.bundleSvc.getAllBundles()
      .then(v=>{
        console.log(v)
        this.bundles = v
      })
      .catch(err=>{
        console.error(err)
      })
  }

}
