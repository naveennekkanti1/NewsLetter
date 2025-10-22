package org.example.service;

import org.example.Constants.constants;
import org.example.Entity.NewsLetterModel;
import org.example.Repository.NewsLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class NewsLetterService {
    @Autowired
    private NewsLetterRepository newsletterRepository;
    @Autowired
    private EmailService emailService;

    public void subscribe(String email) {
        if (newsletterRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already subscribed");
        }
        NewsLetterModel newsLetter=new NewsLetterModel();
        newsLetter.setEmail(email);
        newsLetter.setSubscribed(true);
        newsletterRepository.save(newsLetter);

        emailService.sendWelcomeEmail(email);
    }
    public void weeklyNewsletter(String content) {
        List<NewsLetterModel> subscribedList = newsletterRepository.findByIsSubscribed(true);

        for (NewsLetterModel newsLetter : subscribedList) {
            try {
                emailService.sendWeeklyEmail(newsLetter.getEmail(), content);
            } catch (Exception e) {
                // Log the error instead of returning
                System.err.println("Failed to send email to "
                        + newsLetter.getEmail()
                        + ": " + e.getMessage());
            }
        }
    }

    public void unsubscribe(String email) {
        NewsLetterModel newsLetterModel=newsletterRepository.findByEmail(email).orElseThrow(() ->new RuntimeException("Email not subscribed"));
            newsLetterModel.setSubscribed(false);
            newsletterRepository.save(newsLetterModel);
            log.info("Unsubscribed from " + newsLetterModel.getEmail());
    }
    public List<NewsLetterModel> getAllSubscribers() {
        return newsletterRepository.findAll();
    }

    public boolean isSubscribed(String email) {
        return newsletterRepository.findByEmail(email)
                .map(NewsLetterModel::isSubscribed) // assuming your field is 'isSubscribed'
                .orElse(false);
    }

}
