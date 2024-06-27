import { mensShoesPage1 } from './../../../../../Data/Shoes/shoes';
import { Component } from '@angular/core';
import { filters, singleFilter } from './filterData';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../../state/Product/product.service';
import { Store, select } from '@ngrx/store';
import { AppState } from '../../../../models/AppState';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent {

    filterData: any;
    singleFilterData: any;
    shoes: any;
    products: any;
    levelThree: any;

    constructor(private activatedRoute: ActivatedRoute, private router: Router,
       private productService: ProductService, private store: Store<AppState>){}

    ngOnInit(){
      this.filterData = filters;
      this.singleFilterData = singleFilter;

      this.shoes = mensShoesPage1;

      this.activatedRoute.paramMap.subscribe(
        (params)=>{
          this.levelThree = params.get('levelThree');

          var reqData = {
            colors:[],
            sizes:[],
            minPrice:0,
            maxPrice:5000,
            category: params.get('levelThree'),
            stock:null,
            pageNumber:0,
            pageSize:10,
          }
          this.productService.findProductByCategory(reqData);
        }
      )

      this.activatedRoute.queryParams.subscribe(
        (params)=>{
          const color = params["color"];
          const size = params['size'];
          const price = params["price"];
          const stock = params["stock"];
          const sort = params["sort"];
          const pageNumber = params["pageNumber"];
          const minPrice = price?.split("-")[0];
          const maxPrice = params["stock"];price?.split("-")[1];

          var reqData = {
            colors:color?[color].join(","):[],
            sizes:size,
            minPrice:minPrice?minPrice:0,
            maxPrice:maxPrice?maxPrice:10000,
            category: this.levelThree,
            stock:null,
            pageNumber:pageNumber?pageNumber:0,
            pageSize:10,
            sort: sort?sort:"price_low"
          }

          this.productService.findProductByCategory(reqData)
        }
      )

      this.store.pipe(select((store)=>store.product)).subscribe((product)=>{
        this.products = product?.products?.content;
        console.log("store data ", product.products.content)
      })
    }

    handleMultipleSeclectFilter(value: string, sectionID: string){
      const queryParams = {...this.activatedRoute.snapshot.queryParams};

      // console.log("query params ", queryParams)

      const filterValues = queryParams[sectionID]?queryParams[sectionID].split(","):[];

      const valueIndex = filterValues.indexOf(value);

      if(valueIndex != -1){
        filterValues.splice(valueIndex,1)
      }
      else{
        filterValues.push(value);
      }

      if(filterValues.length > 0){
        queryParams[sectionID] = filterValues.join(",")
      }
      else{
        delete queryParams[sectionID];
      }

      this.router.navigate([], {queryParams})
    }

    handleSingleSelectFilter(value: string, sectionId: string){
      const queryParams = {...this.activatedRoute.snapshot.queryParams};
      queryParams[sectionId] = value;

      this.router.navigate([], {queryParams})
    }
    
}
