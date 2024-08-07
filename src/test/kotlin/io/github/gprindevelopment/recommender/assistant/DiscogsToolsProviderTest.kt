package io.github.gprindevelopment.recommender.assistant

import io.github.gprindevelopment.recommender.discogs.DiscogsReleaseResponse
import io.github.gprindevelopment.recommender.discogs.DiscogsService
import io.github.gprindevelopment.recommender.discogs.DiscogsUser
import io.github.gprindevelopment.recommender.discogs.SimpleVinylRecord
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class DiscogsToolsProviderTest {

    @InjectMockKs
    private lateinit var toolsProvider: DiscogsToolsProvider

    @MockK
    private lateinit var discogsService: DiscogsService

    @Test
    fun `Tool should fetch full vinyl record collection`() {
        val discogsUsername = "test"
        val expected = emptyList<SimpleVinylRecord>()

        every { discogsService.getFullCollection(DiscogsUser(discogsUsername)) } returns expected

        val actual = toolsProvider.fetchFullVinylRecordCollection(discogsUsername)
        assertEquals(expected, actual)
        verify { discogsService.getFullCollection(DiscogsUser(discogsUsername)) }
    }

    @Test
    fun `Tool should fetch get Discogs release from a release ID`() {
        val releaseId = 1
        val expectedResponse = DiscogsReleaseResponse()

        every { discogsService.getRelease(releaseId) } returns expectedResponse

        val actual = toolsProvider.fetchRelease(releaseId)
        assertEquals(expectedResponse, actual)
    }
}