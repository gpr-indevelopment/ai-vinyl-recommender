package io.github.gprindevelopment.recommender.server

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

@ExtendWith(MockKExtension::class)
class ChatMessageHandlerTest {

    @InjectMockKs
    private lateinit var chatMessageHandler: ChatMessageHandler

    @MockK
    private lateinit var wsRecommenderService: WsRecommenderService

    @Test
    fun `Should delegate WebSocket setup to service`() {
        val session = mockk<WebSocketSession>()

        every { wsRecommenderService.setupSession(session) } just runs

        chatMessageHandler.afterConnectionEstablished(session)
        verify { wsRecommenderService.setupSession(session) }
    }

    @Test
    fun `Should delegate new websocket messages to service`() {
        val session = mockk<WebSocketSession>()
        val message = TextMessage("Hello from websockets!")
        every { wsRecommenderService.chat(any(), any()) } just runs

        chatMessageHandler.handleMessage(session, message)
        verify { wsRecommenderService.chat("Hello from websockets!", session) }
    }
}