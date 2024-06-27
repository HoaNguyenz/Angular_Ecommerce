import { adidasShoes } from '../../../../../Data/Adidas/adidas_shoes';
import { Component } from '@angular/core';
import { nikeShoes } from '../../../../../Data/Nike/nike_shoes';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  nikeShoes: any
  adidasShoes: any

  ngOnInit(){
    this.nikeShoes=nikeShoes.slice(0,5);
    this.adidasShoes=adidasShoes.slice(0,5);
  }
}
