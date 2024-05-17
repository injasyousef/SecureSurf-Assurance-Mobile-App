package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerificationCodeActivity extends AppCompatActivity {
    private EditText editTextVerificationCode;
    private String verificationCode;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        verificationCode = generateVerificationCode();
        sendEmailInBackground(email, verificationCode);
    }

    public void buttonSendEmail(View view){
        String stringReceiverEmail = email;
        if (!stringReceiverEmail.isEmpty()) {
            verificationCode = generateVerificationCode();
            sendEmailInBackground(stringReceiverEmail, verificationCode);
        } else {
            Toast.makeText(this, "Please enter a valid receiver email", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private void sendVerificationCode(String receiverEmail, String verificationCode) {
        final String stringSenderEmail = "securesurf.asscsurance@gmail.com";
        final String stringPasswordSenderEmail = "utcclwfcwppcwkux";

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(stringSenderEmail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            mimeMessage.setSubject("Verification Code");
            mimeMessage.setText("Your verification code is: " + verificationCode);

            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private void sendEmailInBackground(String receiverEmail, String verificationCode) {
        new SendEmailTask(receiverEmail, verificationCode).execute();
    }

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private String receiverEmail;
        private String verificationCode;

        SendEmailTask(String receiverEmail, String verificationCode) {
            this.receiverEmail = receiverEmail;
            this.verificationCode = verificationCode;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sendVerificationCode(receiverEmail, verificationCode);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Verification code sent to your email", Toast.LENGTH_LONG).show();
        }
    }

    public void buttonValidateCode(View view) {
        String enteredCode = editTextVerificationCode.getText().toString();

        if (enteredCode.equals(verificationCode)) {
            Intent intent = new Intent(this, AccessibilityOptionsActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            Toast.makeText(this, "Verification code validated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Verification code is incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}