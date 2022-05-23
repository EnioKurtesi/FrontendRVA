import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Fakultet } from 'src/app/models/fakultet';
import { DepartmanService } from 'src/app/services/departman.service';
import { FakultetService } from 'src/app/services/fakultet.service';

@Component({
  selector: 'app-departman-dialog',
  templateUrl: './departman-dialog.component.html',
  styleUrls: ['./departman-dialog.component.css']
})
export class DepartmanDialogComponent implements OnInit {
  public flag!: number;
  subscription!:Subscription;
  fakulteti!: Fakultet[];

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DepartmanDialogComponent>,
    @Inject (MAT_DIALOG_DATA) public data: Departman,
    public departmanServices:DepartmanService,
    public fakultetServices: FakultetService) { }

  ngOnInit(): void {
    this.fakultetServices.getAllFakultete().subscribe(fakultetiIzBaze =>{
      this.fakulteti = fakultetiIzBaze;
    })
  }
  compareTo(a:any, b:any) {
    return a.id == b.id;
  }
  public add():void {
    this.departmanServices.addDepartman(this.data).subscribe(() => {
      this.snackBar.open('Uspešno dodat departman' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom dodavanja departmana', 'Zatvori', {duration:2500})
    }
    );
  }
  public update():void {
    this.departmanServices.updateDepartman(this.data).subscribe(() => {
      this.snackBar.open('Uspešno izmenjen departman' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom izmene departmana', 'Zatvori', {duration:2500})
    }
    );
  }
  public delete():void {
    this.departmanServices.deleteDepartman(this.data.id).subscribe(() => {
      this.snackBar.open('Uspešno obrisan departman' + this.data.naziv, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom brisanja departmana', 'Zatvori', {duration:2500})
    }
    );
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali se', 'Zatvori', {duration:1000});
  }


}
