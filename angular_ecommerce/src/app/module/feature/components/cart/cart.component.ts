import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../../../state/Cart/cart.service';
import { Store, select } from '@ngrx/store';
import { AppState } from '../../../../models/AppState';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {
  // cart = []
  // cartItems:any

  cart: any;
  cartItems: any[] = [];


  constructor(
    private router: Router,
    private cartService: CartService,
    private store: Store<AppState>
  ){

  }


  ngOnInit(){
    this.cartService.getCart()

    this.store.pipe(select((store)=>store.cart)).subscribe((cart)=>{
      this.cartItems = cart.cartItems;
      this.cart = cart?.cart;
      console.log("cart store ", this.cart)
    })
  }
  
  navigateToCheckout(){
    this.router.navigate(["checkout"])
  }
}
