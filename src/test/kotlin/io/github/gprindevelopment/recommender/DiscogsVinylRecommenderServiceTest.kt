package io.github.gprindevelopment.recommender

import dev.langchain4j.service.TokenStream
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class DiscogsVinylRecommenderServiceTest {

    @InjectMockKs
    lateinit var discogsVinylRecommenderService: DiscogsVinylRecommenderService

    @MockK
    lateinit var discogsClient: DiscogsClient

    @MockK
    lateinit var assistant: VinylRecommenderAssistant

    @Test
    fun `Should successfully start recommender chat`() {
        val user = DiscogsUser("some-user")
        val expectedStream = mockk<TokenStream>()
        every { discogsClient.getCollection(user.username) } returns DiscogsResponseMother().discogsResponse()
        every { assistant.chat("Hello!", listOf(VinylRecord("Abbey Road", "The Beatles"),
            VinylRecord("Let It Be", "The Beatles")), any()) } returns expectedStream

        val actual = discogsVinylRecommenderService.startRecommender(user)
        assertEquals(expectedStream, actual)
    }
}

class DiscogsResponseMother {

    fun discogsResponse(): DiscogsCollectionResponse {
        return DiscogsCollectionResponse(
            Pagination(1, 1, 1, 1, Urls("", "")),
            listOf(
                ReleaseResponse(
                    1,
                    BasicInformation(
                        1,
                        "Abbey Road",
                        1969,
                        listOf(ArtistResponse(1, "The Beatles")),
                        listOf(LabelResponse("Apple Records", 1)),
                        listOf("Rock"),
                        listOf("Pop Rock"),
                        listOf(FormatResponse("Vinyl", "1", listOf("LP")))
                    )
                ),
                ReleaseResponse(
                    2,
                    BasicInformation(
                        2,
                        "Let It Be",
                        1970,
                        listOf(ArtistResponse(1, "The Beatles")),
                        listOf(LabelResponse("Apple Records", 1)),
                        listOf("Rock"),
                        listOf("Pop Rock"),
                        listOf(FormatResponse("Vinyl", "1", listOf("LP")))
                    )
                ),
                ReleaseResponse(
                    3,
                    BasicInformation(
                        3,
                        "1962-1966",
                        1973,
                        listOf(ArtistResponse(1, "The Beatles")),
                        listOf(LabelResponse("Apple Records", 1)),
                        listOf("Rock"),
                        listOf("Pop Rock"),
                        listOf(FormatResponse("CD", "1", listOf("CD")))
                    )
                ),
            )
        )
    }
}