import { Injectable } from "@angular/core";
import { BASE_API_URL } from "../../config/api";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Store } from "@ngrx/store";
import { ActivatedRoute, Router } from "@angular/router";
import { catchError, map, of } from "rxjs";
import { findProductByCategoryFailure, findProductByCategorySuccess, findProductByIdFailure, findProductByIdSuccess } from "./product.action";

@Injectable({
    providedIn: 'root',
})

export class ProductService{
    API_BASE_URL = BASE_API_URL;

    private getHeader(): HttpHeaders{
        const token = localStorage.getItem("jwt");
        return new HttpHeaders().set("Authorization", `Bearer ${token}`);
    }

    constructor(private store: Store,
        private http: HttpClient,
        private router: Router,
        private route: ActivatedRoute
    ){}

    findProductByCategory(reqData: any){
        const{colors, sizes, minPrice, maxPrice, category, stock, sort, pageNumber, pageSize} = reqData;

        let params = new HttpParams()
        .set("color", colors)
        .set("size", sizes)
        .set("minPrice", minPrice)
        .set("maxPrice", maxPrice)
        .set("category", category)
        .set("stock", stock)
        .set("sort", sort)
        .set("pageNumber", pageNumber)
        .set("pageSize", pageSize);

        const headers = this.getHeader();

        return this.http.get(`${this.API_BASE_URL}/api/products`,{headers, params}).pipe(
            map((data:any)=>{
                console.log("Products data", data);
                return findProductByCategorySuccess({payload:data})
            }),
            catchError((error:any)=>{
                return of(findProductByCategoryFailure(
                    error.response && error.response.data.message ?
                    error.response.data.message:error.message
                ))
            })
        ).subscribe((action)=>this.store.dispatch(action));
    }

    findProductById(productId: any){
        const headers = this.getHeader();

        return this.http.get(`${this.API_BASE_URL}/api/products/id/${productId}`,{headers}).pipe(
            map((data:any)=>{
                console.log("Product details", data);
                return findProductByIdSuccess({payload:data})
            }),
            catchError((error:any)=>{
                return of(findProductByIdFailure(
                    error.response && error.response.data.message ?
                    error.response.data.message:error.message
                ))
            })
        ).subscribe((action)=>this.store.dispatch(action));
    }
}