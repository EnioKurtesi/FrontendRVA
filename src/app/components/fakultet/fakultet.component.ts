import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Fakultet } from 'src/app/models/fakultet';
import { FakultetService } from 'src/app/services/fakultet.service';
import { FakultetDialogComponent } from '../dialogs/fakultet-dialog/fakultet-dialog.component';

@Component({
  selector: 'app-fakultet',
  templateUrl: './fakultet.component.html',
  styleUrls: ['./fakultet.component.css']
})
export class FakultetComponent implements OnInit, OnDestroy {
  displayedColumns = ['id', 'naziv', 'sediste', 'actions'];
  dataSource!: MatTableDataSource<Fakultet>;
  subscription!: Subscription;

  constructor(private fakultetService: FakultetService,
    private dialog: MatDialog) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }
  public loadData() {
    this.subscription = this.fakultetService.getAllFakultete().subscribe(data => {
      console.log(data);
      this.dataSource = new MatTableDataSource(data);
    },
    (error:Error) => {
      console.log(error.name + '' + error.message);
    });

  }

  public openDialog(flag: number, id?:number, naziv?:string, sediste?: string) {

    const dialogRef = this.dialog.open(FakultetDialogComponent, {data: {id,naziv,sediste}});

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if(result == 1) {
        this.loadData();
      } 
    })
  }

}
