package edu.foro.api.controller;




import edu.foro.api.domain.topic.*;


import edu.foro.api.infra.errors.IntegrityValidity;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Register a new topic",
            description = "",
            tags = { "Topic", "Post" })
    public ResponseEntity<DataDetailTopic> setDataRegistrationTopic(@RequestBody @Valid DataRegistrationTopic dataRegistrationTopic) throws IntegrityValidity {
       var response =  topicService.setDataRegistrationTopic(dataRegistrationTopic);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    @Operation(
            summary = "Gets list of active topics",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailTopic>> topicList(@PageableDefault(page = 0, size = 15 , sort = {"user_id", "course_id"})Pageable pageable
    ) throws IntegrityValidity{
        var response = topicService.listActivated(pageable);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/noresponse")
    @Operation(
            summary = "Gets a list of no response topics",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailTopic>> topicListNoResponse(@PageableDefault(page = 0, size = 15 , sort = {"user_id", "course_id"})Pageable pageable
    ) throws IntegrityValidity{
        var response = topicService.listNoResponse(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/response")
    @Operation(
            summary = "Gets a list of topics with answers",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailTopic>> topicListWhitResponse(@PageableDefault(page = 0, size = 15 , sort = {"user_id", "course_id"})Pageable pageable
    ) throws IntegrityValidity{
        var response = topicService.listWhitResponse(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resolved")
    @Operation(
            summary = "Gets a list of resolved issues",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailTopic>> topicListResolved(@PageableDefault(page = 0, size = 15 , sort = {"user_id", "course_id"})Pageable pageable
    ) throws IntegrityValidity{
        var response = topicService.listResolved(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(
            summary = "Turn off topics",
            description = "",
            tags = { "Topic", "Delete" })
    public ResponseEntity<String> cancelTopicData(@RequestBody @Valid DeleteTopicData deleteTopicData) {
             topicService.cancel(deleteTopicData);
            return ResponseEntity.ok("Topic with ID " + deleteTopicData.id() + " successfully deleted");
    }

    @PutMapping
    @Operation(
            summary = "Records resolved issues",
            description = "",
            tags = { "Topic", "Put" })
    public ResponseEntity<String> markAsResolved(@RequestBody @Valid UpdateResolvedData updateResolvedData) throws IntegrityValidity {
        topicService.markAsResolved(updateResolvedData);
        return ResponseEntity.ok("Topic with ID " + updateResolvedData.id() + " successfully update");
    }
}

