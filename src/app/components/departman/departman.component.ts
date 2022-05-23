import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Fakultet } from 'src/app/models/fakultet';
import { DepartmanService } from 'src/app/services/departman.service';
import { DepartmanDialogComponent } from '../dialogs/departman-dialog/departman-dialog.component';

@Component({
  selector: 'app-departman',
  templateUrl: './departman.component.html',
  styleUrls: ['./departman.component.css']
})
export class DepartmanComponent implements OnInit, OnDestroy {
  displayedColumns = ['id', 'naziv', 'oznaka', 'fakultet', 'actions'];

  dataSource!: MatTableDataSource<Departman>;
  subscription!: Subscription;

  constructor(private departmanService: DepartmanService,
    private dialog: MatDialog) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }
  public loadData() {
    this.subscription = this.departmanService.getAllDepartmane().subscribe(data => {
      console.log(data);
      this.dataSource = new MatTableDataSource(data);
    },
    (error:Error) => {
      console.log(error.name + '' + error.message);
    });

  }
  public openDialog(flag: number, id?:number, naziv?:string, oznaka?: string, fakultet?:Fakultet) {

    const dialogRef = this.dialog.open(DepartmanDialogComponent, {data: {id,naziv,oznaka,fakultet}});

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if(result == 1) {
        this.loadData();
      } 
    })
  }

}
