import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-order-tracker',
  templateUrl: './order-tracker.component.html',
  styleUrl: './order-tracker.component.scss'
})
export class OrderTrackerComponent {
  @Input() activeStep: any;
  @Input() steps: any;
}
