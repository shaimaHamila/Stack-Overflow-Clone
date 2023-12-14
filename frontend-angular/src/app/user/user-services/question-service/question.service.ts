import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage-service/storage.service';

const BASIC_URL = 'http://localhost:5000/api/';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) { }

  postQuestion(questionDto: any): Observable<any> {
    questionDto.userId = StorageService.getUserId();
    console.log("questionDto", questionDto);
    return this.http.post(BASIC_URL + 'question', questionDto,
      { headers: this.createAuthorizationHeadere() })
  }

  getAllQuestions(pageNumber: number): Observable<any> {
    return this.http.get<any>(BASIC_URL + `questions/${pageNumber}`,
      { headers: this.createAuthorizationHeadere() });
  }

  createAuthorizationHeadere() {
    let authHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization', 'Bearer ' + StorageService.getToken()
    )
  }
}