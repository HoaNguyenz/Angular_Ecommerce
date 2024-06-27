import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AdminProductsComponent } from './components/admin-products/admin-products.component';
import { OrdersTableComponent } from './components/orders-table/orders-table.component';
import { CustomersComponent } from './components/customers/customers.component';
import { CreateProductComponent } from './components/create-product/create-product.component';

const routes: Routes = [
  {path: "", component:AdminComponent, children:[
    {path:"", component: DashboardComponent},
    {path:"products", component: AdminProductsComponent},
    {path:"orders", component: OrdersTableComponent},
    {path:"customers", component: CustomersComponent},
    {path:"create-product", component: CreateProductComponent},
  ]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
