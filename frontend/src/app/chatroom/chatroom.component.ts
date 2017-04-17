import { Component, OnInit } from '@angular/core';
import { Message } from '../message';
import { ChatServiceService } from '../chat-service.service';

@Component({
  selector: 'chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {

  errorMessage: String;
  messages: Message[];
  chatroom: string = 'test-chat';


  constructor(private service: ChatServiceService) { }

  ngOnInit() {
    this.service.retrieveMessages(this.chatroom)
        .subscribe(
              messages => this.messages = messages,
              error =>  this.errorMessage = <any>error);
  }

}
