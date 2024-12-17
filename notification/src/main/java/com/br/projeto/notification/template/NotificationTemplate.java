package com.br.projeto.notification.template;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTemplate {
    private String subject = "Cadastro de usuário";
    private String text = "Cadastro realizado com sucesso!";
    private String sender = "PriorizAI";

}
