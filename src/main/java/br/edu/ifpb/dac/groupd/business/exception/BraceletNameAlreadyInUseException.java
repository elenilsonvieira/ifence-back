package br.edu.ifpb.dac.groupd.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BraceletNameAlreadyInUseException extends AbstractException{

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BraceletNameAlreadyInUseException(){
        super(String.format("Nome do Bracelet já em uso."));
    }
    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
