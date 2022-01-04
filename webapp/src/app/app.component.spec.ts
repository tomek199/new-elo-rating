import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { By } from "@angular/platform-browser";
import { Component } from "@angular/core";
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule, MatSidenavModule, MatListModule, BrowserAnimationsModule
      ],
      declarations: [
        AppComponent, HeaderComponentStub
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should contain header', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const header = fixture.debugElement.query(By.directive(HeaderComponentStub));
    expect(header).toBeTruthy()
  });
});

@Component({selector: 'app-header', template: ''})
class HeaderComponentStub { }

