package br.com.zup.SpringDataJPA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public String sendMail(String Text, String To, String From) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(Text);
		message.setTo(To);
		message.setFrom(From);

		try {
			mailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
}