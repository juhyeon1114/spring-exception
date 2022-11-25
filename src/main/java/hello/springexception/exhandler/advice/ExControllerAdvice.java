package hello.springexception.exhandler.advice;

import hello.springexception.exception.UserException;
import hello.springexception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler , @InitBinder 기능을 부여해주는 역할을 한다
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // @ExceptionHandler만 사용하면 상태코드가 200이 됨
    @ExceptionHandler(IllegalArgumentException.class) // @ExceptionHandler : 이 컨트롤러에서 발생한 Exception을 잡음 -> ErrorResult가 json으로 반환됨
    public ErrorResult illegalExHandle(IllegalArgumentException e) { // 에러의 타입과 그 자식들을 처리함
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.error("[exceptionHandle] ex2", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}