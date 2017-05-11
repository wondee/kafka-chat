import { Component, Input, OnInit } from '@angular/core';
import { Message } from '../message';

@Component({
  selector: 'message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.css']
})
export class MessageBoxComponent {

  @Input() message: Message;


  isOwnMessage(): boolean {
    return true;
  }
}
