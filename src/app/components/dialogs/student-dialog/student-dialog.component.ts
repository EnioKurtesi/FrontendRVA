import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { Status } from 'src/app/models/status';
import { Student } from 'src/app/models/student';
import { DepartmanService } from 'src/app/services/departman.service';
import { StatusService } from 'src/app/services/status.service';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-student-dialog',
  templateUrl: './student-dialog.component.html',
  styleUrls: ['./student-dialog.component.css']
})
export class StudentDialogComponent implements OnInit, OnDestroy {

  flag!: number;
  statusi!: Status[];
  subscription!: Subscription
  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<StudentDialogComponent>,
    @Inject (MAT_DIALOG_DATA) public data: Student,
    private statusService: StatusService,
    private studentService: StudentService) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.statusService.getAllStatus().subscribe(data => {
      this.statusi = data;
    })
  }

  compareTo(a:any,b:any) {
return a.id == b.id;
  }
  public add():void {
    this.studentService.addStudent(this.data).subscribe(() => {
      this.snackBar.open('Uspešno dodat student' + this.data.ime, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom dodavanja studenta', 'Zatvori', {duration:2500})
    }
    );
  }
  public update():void {
    this.studentService.updateStudent(this.data).subscribe(() => {
      this.snackBar.open('Uspešno izmenjen student' + this.data.ime, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom izmene studenta', 'Zatvori', {duration:2500})
    }
    );
  }
  public delete():void {
    this.studentService.deleteStudent(this.data.id).subscribe(() => {
      this.snackBar.open('Uspešno obrisan student' + this.data.ime, 'OK', {duration:2500})
    },
    (error:Error) => {
      this.snackBar.open('Doslo je do greske prilikom brisanja studenta', 'Zatvori', {duration:2500})
    }
    );
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali se', 'Zatvori', {duration:1000});
  }



}
