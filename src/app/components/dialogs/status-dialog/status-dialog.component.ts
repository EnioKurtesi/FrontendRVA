import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Status } from 'src/app/models/status';
import { StatusService } from 'src/app/services/status.service';

@Component({
  selector: 'app-status-dialog',
  templateUrl: './status-dialog.component.html',
  styleUrls: ['./status-dialog.component.css']
})
export class StatusDialogComponent implements OnInit {

  public flag!: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<StatusDialogComponent>,
    @Inject (MAT_DIALOG_DATA) public data: Status,
    public statusServices: StatusService
    ) { }

  ngOnInit(): void {
  }

  public add():void {
    this.statusServices.addStatus(this.data).subscribe(() => {
      this.snackBar.open('Uspešno dodat status' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom dodavanja statusa', 'Zatvori', {duration:2500})
    }
    );
  }

  public update():void {
    this.statusServices.updateStatus(this.data).subscribe(() => {
      this.snackBar.open('Uspešno izmenjen status' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom izmene statusa', 'Zatvori', {duration:2500})
    }
    );
  }

  public delete():void {
    this.statusServices.deleteStatus(this.data.id).subscribe(() => {
      this.snackBar.open('Uspešno obrisan status' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom brisanja statusa', 'Zatvori', {duration:2500})
    }
    );
  }


  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali se', 'Zatvori', {duration:1000});
  }

}
