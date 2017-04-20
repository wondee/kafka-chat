import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { LoginResult } from '../login-result';

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  private user: string;
  private chatroom: string;

  @Output() onLogin = new EventEmitter<LoginResult>();

  constructor() { }

  ngOnInit() {
  }

  login() {
    this.onLogin.emit( { user: this.user, chatroom: this.chatroom } )
  }

}
