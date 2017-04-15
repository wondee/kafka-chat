import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css']
})
export class ChatroomComponent implements OnInit {


  messages = [
    {
        text: "Hallo, ich wollte mal fragen wie man ein Angular Frontend in eine Spring Boot Anwendung integriert",
        user: "Peter",
        time: "15:33"
    },
    {
        text: "Hallo Peter, ja dann guck doch...",
        user: "Fritz",
        time: "15:34"
    }
  ];

  constructor() { }



  ngOnInit() {
    
  }

}
