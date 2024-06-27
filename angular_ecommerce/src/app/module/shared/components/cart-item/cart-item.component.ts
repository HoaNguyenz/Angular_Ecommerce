
import { Component, Input } from '@angular/core';
import { CartService } from '../../../../state/Cart/cart.service';
import { Store, select } from '@ngrx/store';
import { AppModule } from '../../../../app.module';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.scss'
})
export class CartItemComponent {

  @Input() showButton: any;
  @Input() cartItem: any;

  constructor(
    private cartService: CartService,
  ){}

  handleUpdateCartItem(num: Number){
    console.log("cartItem", this.cartItem)
    this.cartService.updateCartItem({
      cartItemId: this.cartItem.id,
      data:{ 
        quantity: num + this.cartItem.quantity
      }
    })
  }

  removeCartItem(){
    console.log("removed cart item")

    this.cartService.removeCartItem(this.cartItem.id)
  }
}
