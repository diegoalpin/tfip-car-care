import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { AuthActivateRouteGuard } from './routeguards/auth.routeguard';
import { NavbarComponent } from './components/navbar/navbar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CarComponent } from './components/car/car.component';
import { MaintenanceComponent } from './components/maintenance/maintenance.component';
import { LogoutComponent } from './components/logout/logout.component';
import { MaintenanceAllComponent } from './components/maintenance-all/maintenance-all.component';
import { AccountComponent } from './components/account/account.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { AddCarComponent } from './components/add-car/add-car.component';
import { AddMaintenanceComponent } from './components/add-maintenance/add-maintenance.component';
import { EditMaintenanceComponent } from './components/edit-maintenance/edit-maintenance.component';
import { StripeSuccessComponent } from './components/stripe-success/stripe-success.component';
import { StripeCancelComponent } from './components/stripe-cancel/stripe-cancel.component';
import { StripeCheckoutComponent } from './components/stripe-checkout/stripe-checkout.component';
import { TransferListComponent } from './components/transfer-list/transfer-list.component';

const routes: Routes = [
  {path:'signup', component: SignupComponent},
  {path:'login', component: LoginComponent},
  {path:'', component: LoginComponent},
  
  {path:'dashboard', component: DashboardComponent},
  {path:'account', component: AccountComponent},
  {path:'car/add',component:AddCarComponent},
  {path:'car/:id',component:CarComponent},
  {path:'transferlist',component:TransferListComponent,},

  {path:'maintenance/all',component:MaintenanceAllComponent,},
  {path:'maintenance/add',component:AddMaintenanceComponent,},
  {path:'maintenance/edit/:id',component:EditMaintenanceComponent,},
  {path:'maintenance/:id',component:MaintenanceComponent,},
  
  {path:'checkout',component:StripeCheckoutComponent,},
  {path:'pay/cancel',component:StripeCancelComponent,},
  {path:'pay/success',component:StripeSuccessComponent,},
  
  {path:'about-us',component:AboutUsComponent},
  {path:'logout',component:LogoutComponent},
  
  // landingpage
  // forgotpassword


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
