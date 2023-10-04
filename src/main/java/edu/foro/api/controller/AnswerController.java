package edu.foro.api.controller;



import edu.foro.api.domain.answer.AnswerService;
import edu.foro.api.domain.answer.DataDetailAnswer;
import edu.foro.api.domain.answer.DataRegistrationAnswer;
import edu.foro.api.domain.answer.DelateAnswerData;
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
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;



    @PostMapping
    @Operation(
            summary = "Records a response to a topic",
            description = "",
            tags = { "Answer", "Post" })
    public ResponseEntity<DataDetailAnswer> setDataRegistrationAnswer(@RequestBody @Valid DataRegistrationAnswer dataRegistrationAnswer) throws IntegrityValidity {
        var response =  answerService.setDataRegistrationAnswer(dataRegistrationAnswer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Gets list of topics with response",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailAnswer>> answerList(@PageableDefault(page = 0, size = 15 , sort = {"id"})Pageable pageable){
        var response = answerService.listarActivated(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resolved")
    @Operation(
            summary = "Gets list of resolved issues",
            description = "",
            tags = { "Topic", "Get" })
    public ResponseEntity<Page<DataDetailAnswer>> resolvedList(@PageableDefault(page = 0, size = 15 , sort = {"id"})Pageable pageable){
        var response = answerService.listarResolved(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(
            summary = "Cancel a reply",
            description = "",
            tags = { "Answer", "Delete" })
    public  ResponseEntity cancelAnswerData(@RequestBody @Valid DelateAnswerData delateAnswerData) throws IntegrityValidity {
        answerService.cancel(delateAnswerData);
        return ResponseEntity.ok("Answer with ID " + delateAnswerData.id() + " successfully deleted");
    }


}


