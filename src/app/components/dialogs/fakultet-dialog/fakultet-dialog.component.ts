import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Fakultet } from 'src/app/models/fakultet';
import { FakultetService } from 'src/app/services/fakultet.service';

@Component({
  selector: 'app-fakultet-dialog',
  templateUrl: './fakultet-dialog.component.html',
  styleUrls: ['./fakultet-dialog.component.css']
})
export class FakultetDialogComponent implements OnInit {

  public flag!: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<FakultetDialogComponent>,
    @Inject (MAT_DIALOG_DATA) public data: Fakultet,
    public fakultetServices: FakultetService
    ) { }

  ngOnInit(): void {
  }
  public add():void {
    this.fakultetServices.addFakultet(this.data).subscribe(() => {
      this.snackBar.open('Uspešno dodat fakultet' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom dodavanja fakulteta', 'Zatvori', {duration:2500})
    }
    );
  }
  public update():void {
    this.fakultetServices.updateFakultet(this.data).subscribe(() => {
      this.snackBar.open('Uspešno izmenjen fakultet' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom izmene fakultet', 'Zatvori', {duration:2500})
    }
    );
  }
  public delete():void {
    this.fakultetServices.deleteFakultet(this.data.id).subscribe(() => {
      this.snackBar.open('Uspešno obrisan fakultet' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom brisanja fakultet', 'Zatvori', {duration:2500})
    }
    );
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali se', 'Zatvori', {duration:1000});
  }





}
