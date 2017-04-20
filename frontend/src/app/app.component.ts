import { Component } from '@angular/core';
import { LoginResult } from "./login-result";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'The Kafka Chat';
  session: LoginResult = null;

  isLoggedIn(): boolean {
    return this.session != null;
  }

  onLogin(result: LoginResult) {
    this.session = result;
  }

}
