import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import { Message }        from './message'; 

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class ChatServiceService {

  private roomsUrl = 'http://localhost:8080/api/room/';  // URL to web API

  constructor(private http: Http) { }

  retrieveMessages(chat: String): Observable<Message[]> {
    return this.http.get(this.roomsUrl + chat)
      .map(this.extractData)
      .catch(this.handleError)

  }
  
  sendMessage(chat: String, msg: Message): void {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    
    this.http.post(this.roomsUrl + chat + "/message", msg, options)
      .catch(this.handleError)
      .subscribe();
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
