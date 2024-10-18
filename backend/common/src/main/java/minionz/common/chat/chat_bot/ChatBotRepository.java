package minionz.common.chat.chat_bot;

import minionz.common.chat.chat_bot.model.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {

    List<ChatBot> findAllByUser_UserIdOrderByCreatedAtAsc(Long userId);
}
