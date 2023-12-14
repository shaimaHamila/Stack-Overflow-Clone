import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { Observable } from 'rxjs';


const BASIC_URL = 'http://localhost:5000/api/';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) { }

  postAnswer(answerDto: any): Observable<any> {
    return this.http.post(BASIC_URL + 'answer', answerDto,
      { headers: this.createAuthorizationHeadere() })
  }

  createAuthorizationHeadere() {
    let authHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization', 'Bearer ' + StorageService.getToken()
    )
  }
}
