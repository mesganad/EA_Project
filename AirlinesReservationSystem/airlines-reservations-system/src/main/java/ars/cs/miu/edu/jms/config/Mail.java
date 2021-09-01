package ars.cs.miu.edu.jms.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Email
    private String mailFrom;

    @Email
    private String mailTo;


    private String mailSubject;

    @NonNull
    private String mailContent;

    @Size(min = 1, max = 2000)
    private String contentType;

}
