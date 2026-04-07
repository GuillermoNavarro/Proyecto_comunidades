package com.comunidad.comunidad_backend.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void enviarMailBienvenida(String email, String password){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("sistema@comunidades.com");
        mail.setTo(email);
        mail.setSubject("Alta en Aplicacion");
        mail.setText("Su usuario ha sido dado de alta en la aplicacion.\nPuede acceder con las siguientes credenciales \n\nUsuario: " + email + "\nContraseña: "+ password + "\n\nLa primera vez que acceda sera necesario cambiar la contraseña.");
        javaMailSender.send(mail);
    }

    @Async
    public void enviarMailContraseña(String email, String password){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("sistema@comunidades.com");
        mail.setTo(email);
        mail.setSubject("Cambio de Contraseña");
        mail.setText("A peticion suya se ha cambiado la contrseñade acceso. \nRecuerde que necesitara cambiarla la primera vez que acceda \n\nContraseña provisional: " + password);
        javaMailSender.send(mail);
    }

    
}
