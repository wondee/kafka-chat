import { Injectable }     from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import { Message }        from './message'; 

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class ChatServiceService {

  private roomsUrl = 'http://localhost:8080/api/room/';  // URL to web API

  private messages = [
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

  constructor(private http: Http) { }

  retrieveMessages(chat: String): Observable<Message[]> {
    return this.http.get(this.roomsUrl + chat)
      .map(this.extractData)
      .catch(this.handleError)

  }
  
  // code from google:
  private extractData(res: Response) {
    let body = res.json();
    
    console.log(body);

    return body || { };
  }

  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
