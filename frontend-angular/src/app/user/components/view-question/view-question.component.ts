import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnswerService } from '../../user-services/answer-services/answer.service';
import { S } from '@angular/cdk/keycodes';
import { StorageService } from '../../../auth-services/storage-service/storage.service';

@Component({
  selector: 'app-view-question',
  templateUrl: './view-question.component.html',
  styleUrl: './view-question.component.scss'
})
export class ViewQuestionComponent {

  questionId: number = this.activatedRoute.snapshot.params["questionId"];
  question: any;
  validateForm!: FormGroup;
  constructor(private questionService: QuestionService,
    private answerService: AnswerService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.validateForm = this.formBuilder.group({
      body: [null, Validators.required]
    });
    this.getQuestionById();
  }

  getQuestionById() {
    this.questionService.getQuestionById(this.questionId).subscribe(data => {
      console.log("Get question by id from view-question", data);
      this.question = data.questionDTO;
      console.log("question question title", this.question);

    })
  }

  addAnswer() {
    const data = this.validateForm.value;
    data.questionId = this.questionId;
    data.userId = StorageService.getUserId();
    console.log("Answer To add", data)

    this.answerService.postAnswer(data).subscribe(data => {
      console.log("Answer added", data)
    });
  }


}
