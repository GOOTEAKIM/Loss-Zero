package losszero.losszero.controller.realtime;

import losszero.losszero.dto.realtime.RealtimeCircumstanceDTO;
import losszero.losszero.service.realtime.RealtimeCircumstanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realtime/circumstance")
public class RealtimeCircumstanceController {

    private final RealtimeCircumstanceService realtimeCircumstanceService;

    @PostMapping
    public ResponseEntity<Map<String, String>> saveCircumstanceData(
            @RequestParam int lineId, @RequestBody RealtimeCircumstanceDTO data) {

        realtimeCircumstanceService.saveCircumstanceData(lineId, data);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "데이터 전송에 성공했습니다."));
    }

    @GetMapping
    public SseEmitter streamRealtimeData(@RequestParam int lineId) {

        SseEmitter emitter = new SseEmitter();
        realtimeCircumstanceService.streamRealtimeData(lineId, emitter);
        return emitter;
    }
}
