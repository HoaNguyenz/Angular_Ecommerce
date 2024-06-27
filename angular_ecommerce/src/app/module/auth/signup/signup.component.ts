import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { AuthService } from '../../../state/Auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  @Input() changeTemplate: any;

  constructor(private formBuilder: FormBuilder, private store: Store,
    private authService: AuthService
  ){

  }

  loginForm: FormGroup =this.formBuilder.group({
    firstName:["", [Validators.required]],
    lastName:["", [Validators.required]],
    email: ['',[Validators.required, Validators.email]],
    password: ["", [Validators.required, Validators.minLength(8)]]
  })

  submitForm(): void{
    if(this.loginForm.valid){
      this.authService.register(this.loginForm.value);
      console.log("Login req data", this.loginForm.value);
    }
  }
}
