import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import { AuthComponent } from '../../../auth/auth.component';
import { UserService } from '../../../../state/User/user.service';
import { Store, select } from '@ngrx/store';
import { AppState } from '../../../../models/AppState';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'] // Corrected property name: styleUrls
})
export class NavbarComponent {

  currentSection: any;
  isNavbarContentOpen: boolean = false;
  userProfile: any

  constructor(private router: Router, private dialog: MatDialog,
    private userService: UserService,
    private store: Store<AppState>
  ){

  }
  openNavBarContent(section: any) {
    this.isNavbarContentOpen = true;
    this.currentSection = section;
  }

  closeNavbarContent() {
    this.isNavbarContentOpen = false;
  }

  navigateTo(path: any) {
    this.router.navigate([path]);
  }

  ngOnInit(){
    if(localStorage.getItem("jwt")) this.userService.getUserProfile();

    this.store.pipe(select((store)=>store.user)).subscribe((user)=>{
      this.userProfile = user.userProfile;

      if(user.userProfile){
        this.dialog.closeAll()
      }
      console.log("user", user)
    })
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const modalContainer = document.querySelector(".modal-container");
    const openButtons = document.querySelectorAll(".open_button");

    let clickInsideButton = false;

    // if(openButtons){
    //   clickInsideButton = true;
    // }

    openButtons.forEach((button: Element) => {
      if (button.contains(event.target as Node)) {
        clickInsideButton = true;
      }
    });

    if (modalContainer && !clickInsideButton && this.isNavbarContentOpen) {
      this.closeNavbarContent();
    }
  }

  handleOpenLoginModel=()=>{
    console.log("Handle open login module")
    this.dialog.open(AuthComponent,{
      width: "400px",
      disableClose: false
    })
  }

  handleLogout=()=>{
    this.userService.logout()
  }
}
