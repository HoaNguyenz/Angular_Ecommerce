import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { FeatureModule } from './module/feature/feature.module';
import { SharedModule } from './module/shared/shared.module';
import { AdminModule } from './module/admin/admin.module';
import { StoreModule } from '@ngrx/store';
import { AuthModule } from './module/auth/auth.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { authReducer } from './state/Auth/auth.reducer';
import { userReducer } from './state/User/user.reducer';
import { HttpClientModule } from '@angular/common/http';
import { productReducer } from './state/Product/product.reducer';
import { cartReducer } from './state/Cart/cart.reducer';
import { orderReducer } from './state/Order/order.reducer';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FeatureModule,
    SharedModule,
    AdminModule,
    AuthModule,
    StoreModule.forRoot(
      {auth: authReducer,
        user:userReducer,
        product:productReducer,
        cart: cartReducer,
        order: orderReducer
      }),
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
