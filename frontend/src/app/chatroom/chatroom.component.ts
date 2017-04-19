import { Component, OnInit } from '@angular/core';
import { Message } from '../message';
import { ChatServiceService } from '../chat-service.service';
import { StompService } from 'ng2-stomp-service';

@Component({
  selector: 'chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {

  private stompConfig = {
    host: 'http://localhost:8080/messages',
    debug: true
  }

  errorMessage: String;
  messages: Message[];
  chatroom: string = 'test-chat';
  user: string = 'Markus';

  constructor(private service: ChatServiceService, private stomp: StompService) { 
  }

  ngOnInit() {
    this.service.retrieveMessages(this.chatroom)
        .subscribe(
              messages => this.messages = messages,
              error =>  this.errorMessage = <any>error);

    this.subscribeChat();
  }

  sendMessage(msgText: string) {

    this.service.sendMessage(this.chatroom, { 
      text: msgText, 
      user: this.user, 
      room: this.chatroom
    });
  }

  private subscribeChat() {
    this.stomp.configure(this.stompConfig);
    this.stomp.startConnect().then (() => {
      console.log('connected to server');
      this.stomp.subscribe('/chat/' + this.chatroom, this.handleMessage);
    });
  }

  private handleMessage = (data: Message) => {
    console.log(data);
    this.messages.push(data);

  }

}
