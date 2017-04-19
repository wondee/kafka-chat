import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http, JsonpModule } from '@angular/http';

import { StompService } from 'ng2-stomp-service';

import { AppComponent } from './app.component';
import { MessageBoxComponent } from './message-box/message-box.component';
import { ChatroomComponent } from './chatroom/chatroom.component';

import { ChatServiceService } from './chat-service.service';


@NgModule({
  declarations: [
    AppComponent,
    MessageBoxComponent,
    ChatroomComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule
  ],
  providers: [ChatServiceService, StompService],
  bootstrap: [AppComponent]
})
export class AppModule { }
