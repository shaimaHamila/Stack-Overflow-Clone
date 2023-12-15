package com.stackoverflow.services.question;

import com.stackoverflow.dtos.AllQuestionResponseDto;
import com.stackoverflow.dtos.AnswerDto;
import com.stackoverflow.dtos.QuestionDTO;
import com.stackoverflow.dtos.SingleQuestionDto;
import com.stackoverflow.entities.Answer;
import com.stackoverflow.entities.Question;
import com.stackoverflow.entities.User;
import com.stackoverflow.repositories.AnswerRepository;
import com.stackoverflow.repositories.ImageRepository;
import com.stackoverflow.repositories.QuestionRepository;
import com.stackoverflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements  QuestionService{
    //Number of questions per page
    public static final int SEARCH_RESULT_PER_PAGE = 5;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

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

        if(optionalQuestion.isPresent()){
            //get the question and set it to singleQuestionDto
            SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
            singleQuestionDto.setQuestionDTO(optionalQuestion.get().getQuestionDto());

            //get the question's answers and set it to singleQuestionDto
            List<AnswerDto> answerDtoList = new ArrayList<>();
            List<Answer> answerList = answerRepository.findAllByQuestionId(questionId);
            for (Answer answer: answerList) {
                AnswerDto answerDto = answer.getAnswerDto();
                answerDto.setFile(imageRepository.findByAnswer(answer));
                answerDtoList.add(answerDto);
            }
            singleQuestionDto.setAnswerDtoList(answerDtoList);
            return singleQuestionDto;
        }
        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestionsByUserId(Long userId, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Question> questionsPage =  questionRepository.findAllByUserId(userId, paging);

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();

        allQuestionResponseDto.setQuestionDTOList(questionsPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionResponseDto.setPageNumber(questionsPage.getPageable().getPageNumber());
        allQuestionResponseDto.setTotalPages(questionsPage.getTotalPages());
        return allQuestionResponseDto;
    }
}
