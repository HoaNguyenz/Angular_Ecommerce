import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from './state/User/user.service';
import { Store, select } from '@ngrx/store';
import { AppState } from './models/AppState';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angular_ecommerce';

  constructor(private router: Router, private dialog: MatDialog,
    private userService: UserService,
    private store: Store<AppState>
  ){

  }

  ngOnInit(){
    if(localStorage.getItem("jwt")) this.userService.getUserProfile();

    this.store.pipe(select((store)=>store.auth)).subscribe((user)=>{
      this.userService.getUserProfile()
      console.log("store", this.store)
    })
  }
}
