import { Component, Input, OnInit, AfterViewChecked, ElementRef, ViewChild } from '@angular/core';
import { Message } from '../message';
import { ChatServiceService } from '../chat-service.service';
import { StompService } from 'ng2-stomp-service';

@Component({
  selector: 'chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit, AfterViewChecked {

  private stompConfig = {
    host: 'http://localhost:8080/messages',
    debug: true
  }

  @ViewChild('chatView') private chatView: ElementRef;

  @Input() chatroom: string;
  @Input() user: string;
  
  errorMessage: string;

  messages: Message[];

  constructor(private service: ChatServiceService, private stomp: StompService) { 
  }

  ngOnInit() {
    this.service.retrieveMessages(this.chatroom)
        .subscribe(
              messages => this.messages = messages,
              error =>  this.errorMessage = <any>error);

    this.subscribeChat();
    this.scrollToBottom();
  }

  ngAfterViewChecked() {        
    this.scrollToBottom();        
  } 

  sendMessage(msgText: string) {

    this.service.sendMessage(this.chatroom, { 
      text: msgText, 
      user: this.user, 
      room: this.chatroom
    });
  }
  
  private scrollToBottom() {
    let nativeElem = this.chatView.nativeElement;
    nativeElem.scrollTop = nativeElem.scrollHeight;
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
