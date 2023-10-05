package edu.foro.api.controller;



import edu.foro.api.domain.answer.AnswerService;
import edu.foro.api.domain.answer.DataDetailAnswer;
import edu.foro.api.domain.answer.DataRegistrationAnswer;
import edu.foro.api.domain.answer.DelateAnswerData;
import edu.foro.api.infra.errors.IntegrityValidity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@SecurityRequirement(name = "bearer-key")
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


