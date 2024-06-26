package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.List;


@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // @ApiOperation()
    // @ApiOperation 어노테이션을 사용하려면 Springfox 라이브러리를 사용해야 함.
    // 하지만 Spring Boot 3 버전부터는 Springfox 대신 Springdoc OpenAPI를 사용할 것
    // 어노테이션은 @Operation 사용
    @Operation(summary = "일기 작성", description = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장")
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate date,
                     @RequestBody
                     String text) {
        diaryService.createDiary(date, text);
    }

    @Operation(summary = "선택날짜 일기", description = "선택한 날짜의 모든 일기 데이터 조회")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          LocalDate date) {
        return diaryService.readDiary(date);
    }

    @Operation(summary = "기간날짜 일기", description = "선택한 기간중의 모든 일기 데이터 조회")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(description = "첫번째 날짜", example = "2024-06-25")
                            LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @Parameter(description = "마지막 날짜", example = "2024-06-25")
                            LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @Operation(summary = "일기 업데이트", description = "특정 날짜의 일기 데이터 업데이트")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate date,
                     @RequestBody
                     String text) {
        diaryService.updateDiary(date, text);
    }

    @Operation(summary = "일기 삭제", description = "특정 날짜의 일기 데이터 삭제")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     LocalDate date) {
        diaryService.deleteDiary(date);

    }
}
