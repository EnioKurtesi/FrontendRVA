import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatExpansionModule } from '@angular/material/expansion';
import { MatSidenavModule } from '@angular/material/sidenav';
import { HomeComponent } from './components/core/home/home.component';
import { AuthorComponent } from './components/core/author/author.component';
import { AboutComponent } from './components/core/about/about.component';
import { StudentComponent } from './components/student/student.component';
import { FakultetComponent } from './components/fakultet/fakultet.component';
import { StatusComponent } from './components/status/status.component';
import { DepartmanComponent } from './components/departman/departman.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthorComponent,
    AboutComponent,
    StudentComponent,
    FakultetComponent,
    StatusComponent,
    DepartmanComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatExpansionModule,
    MatSidenavModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
