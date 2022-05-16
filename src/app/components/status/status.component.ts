import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Status } from 'src/app/models/status';
import { StatusService } from 'src/app/services/status.service';
import { StatusDialogComponent } from '../dialogs/status-dialog/status-dialog.component';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit, OnDestroy {

  displayedColumns = ['id', 'naziv', 'oznaka', 'actions'];
  dataSource!: MatTableDataSource<Status>;
  subscription!: Subscription;

  constructor(private statusService: StatusService,
    private dialog: MatDialog) { }
  ngOnDestroy(): void {
 
      this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.subscription = this.statusService.getAllStatus().subscribe(data => {
      console.log(data);
      this.dataSource = new MatTableDataSource(data);
    },
    (error:Error) => {
      console.log(error.name + '' + error.message);
    });

  }

  public openDialog(flag: number, id?:number, naziv?:string, oznaka?: string) {

    const dialogRef = this.dialog.open(StatusDialogComponent, {data: {id,naziv,oznaka}});

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if(result == 1) {
        this.loadData();
      } 
    })
  }

}
