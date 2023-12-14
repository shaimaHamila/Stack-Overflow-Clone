package com.stackoverflow.services.question;

import com.stackoverflow.dtos.AllQuestionResponseDto;
import com.stackoverflow.dtos.QuestionDTO;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDto);

    AllQuestionResponseDto getAllQuestions(int pageNumber);
}
