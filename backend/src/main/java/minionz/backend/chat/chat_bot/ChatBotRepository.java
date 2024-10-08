package minionz.backend.chat.chat_bot;

import minionz.backend.chat.chat_bot.model.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {
}
