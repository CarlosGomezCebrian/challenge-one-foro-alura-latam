package edu.foro.api.controller;




import edu.foro.api.domain.topic.*;


import edu.foro.api.infra.errors.IntegrityValidity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;


    @PostMapping
    public ResponseEntity<DataDetailTopic> setDataRegistrationTopic(@RequestBody @Valid DataRegistrationTopic dataRegistrationTopic) throws IntegrityValidity {
       var response =  topicService.setDataRegistrationTopic(dataRegistrationTopic);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<Page<DataDetailTopic>> topicList(@PageableDefault(page = 0, size = 15 , sort = {"user_id", "course_id"})Pageable pageable) throws IntegrityValidity{
        var response = topicService.listarActivated(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> cancelTopicData(@RequestBody @Valid DeleteTopicData deleteTopicData) {
             topicService.cancel(deleteTopicData);
            return ResponseEntity.ok("Topic with ID " + deleteTopicData.id() + " successfully deleted");
    }

    @PutMapping
    public ResponseEntity<String> markAsSolved(@RequestBody @Valid UpdateSolvedData updateSolvedData) throws IntegrityValidity {
        topicService.markAsSolved(updateSolvedData);
        return ResponseEntity.ok("Topic with ID " + updateSolvedData.id() + " successfully update");
    }
}

