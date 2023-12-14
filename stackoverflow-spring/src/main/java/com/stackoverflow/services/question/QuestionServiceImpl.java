package com.stackoverflow.services.question;

import com.stackoverflow.dtos.AllQuestionResponseDto;
import com.stackoverflow.dtos.QuestionDTO;
import com.stackoverflow.dtos.SingleQuestionDto;
import com.stackoverflow.entities.Question;
import com.stackoverflow.entities.User;
import com.stackoverflow.repositories.QuestionRepository;
import com.stackoverflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements  QuestionService{
    //Number of questions per page
    public static final int SEARCH_RESULT_PER_PAGE = 5;


    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDto) {
        Optional<User> optionalUser = userRepository.findById(questionDto.getUserId());
        if (optionalUser.isPresent()){
            Question question = new Question();
            question.setTitle(questionDto.getTitle());
            question.setBody(questionDto.getBody());
            question.setCreatedDate(new Date());
            question.setTags(questionDto.getTags());
            question.setUser(optionalUser.get());
            Question createdQuestion =  questionRepository.save(question);

            QuestionDTO createdQuestionDto = new QuestionDTO();
            createdQuestionDto.setId(createdQuestion.getId());
            createdQuestionDto.setTitle(createdQuestion.getTitle());
            return createdQuestionDto;
        }
        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestions(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage =  questionRepository.findAll(paging);

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();

        allQuestionResponseDto.setQuestionDTOList(questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionResponseDto;
    }

    @Override
    public SingleQuestionDto getQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
        optionalQuestion.ifPresent(question -> singleQuestionDto.setQuestionDTO(question.getQuestionDto()));
        return singleQuestionDto;
    }
}
