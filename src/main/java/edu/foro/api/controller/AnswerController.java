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
            summary = "registra una respuesta de un tema",
            description = "",
            tags = { "Respuesta", "Post" })
    public ResponseEntity<DataDetailAnswer> setDataRegistrationAnswer(@RequestBody @Valid DataRegistrationAnswer dataRegistrationAnswer) throws IntegrityValidity {
        var response =  answerService.setDataRegistrationAnswer(dataRegistrationAnswer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "obtiene listado de temas con respuesta",
            description = "",
            tags = { "Temas", "Get" })
    public ResponseEntity<Page<DataDetailAnswer>> answerList(@PageableDefault(page = 0, size = 15 , sort = {"id"})Pageable pageable){
        var response = answerService.listarActivated(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resolved")
    @Operation(
            summary = "obtiene listado de temas resueltos",
            description = "",
            tags = { "Temas", "Get" })
    public ResponseEntity<Page<DataDetailAnswer>> resolvedList(@PageableDefault(page = 0, size = 15 , sort = {"id"})Pageable pageable){
        var response = answerService.listarResolved(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(
            summary = "cancela una respuesta",
            description = "",
            tags = { "Respuesta", "Delete" })
    public  ResponseEntity cancelAnswerData(@RequestBody @Valid DelateAnswerData delateAnswerData) throws IntegrityValidity {
        answerService.cancel(delateAnswerData);
        return ResponseEntity.ok("Answer with ID " + delateAnswerData.id() + " successfully deleted");
    }


}


