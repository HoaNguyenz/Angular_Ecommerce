import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from '../../../../../state/Order/order.service';

@Component({
  selector: 'app-address-form',
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.scss'
})
export class AddressFormComponent {

  addresses = [1,1,1];
  myForm: FormGroup=this.formBuilder.group({
    firstName: ["", Validators.required],
    lastName: ["", Validators.required],
    streetAddress: ["", Validators.required],
    city: ["", Validators.required],
    mobile: ["", Validators.required],
  })
  constructor(
    private formBuilder: FormBuilder,
    private orderService: OrderService
  ){

  }

  handleCreateOrder(item:any){

  }

  

  handleSubmit=()=>{
    const formValue = this.myForm.value;
    this.orderService.createOrder(formValue)
    console.log("form data", formValue)
  }
}
