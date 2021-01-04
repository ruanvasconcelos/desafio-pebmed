package com.example.desafiopebmed.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.desafiopebmed.BaseUnitTest
import com.example.desafiopebmed.source.local.Database
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class MedicalListRepositoryTest : BaseUnitTest() {

    private lateinit var webServiceAPI: WebServiceAPI
    private lateinit var databaseMockk: Database

    @Before
    override fun before() {
        super.before()
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        webServiceAPI = retrofit.create(WebServiceAPI::class.java)
        databaseMockk =
            Room.inMemoryDatabaseBuilder(context, Database::class.java).allowMainThreadQueries()
                .build()
    }

    @Test
    fun testLoadMedicalListWithValidResponse() {

        enqueueResponse("medicallist/valid_response.json")
        val medicalListRepository = spyk(MedicalListRepository(webServiceAPI, databaseMockk))

        val testObserver = medicalListRepository.loadMedicalList().test()

        testObserver.await()
        testObserver.assertComplete()
        testObserver.assertValue {
            it.size == 15
                    && it.firstOrNull()?.category?.name?.equals("Cuidados Paliativos") == true
                    && it.firstOrNull()?.category?.id == 0
        }
        verify (exactly = 1) { medicalListRepository.saveLocalItemList(any()) }
        verify (exactly = 0) { medicalListRepository.recoverLocalItemList() }
    }

    @Test
    fun testLoadMedicalListWithEmptyResponse() {

        enqueueResponse("medicallist/empty_response.json")
        val medicalListRepository = spyk(MedicalListRepository(webServiceAPI, databaseMockk))

        val testObserver = medicalListRepository.loadMedicalList().test()

        testObserver.await()
        testObserver.assertComplete()
        testObserver.assertValue {
            it.isEmpty()
        }
        verify (exactly = 0) { medicalListRepository.saveLocalItemList(any()) }
        verify (exactly = 0) { medicalListRepository.recoverLocalItemList() }
    }

    @Test
    fun testLoadMedicalListWithHttpError() {

        enqueueResponse("medicallist/valid_response.json", 400)

        val medicalListRepository = spyk(MedicalListRepository(webServiceAPI, databaseMockk))
        medicalListRepository.loadMedicalList().test()

        verify (exactly = 1) { medicalListRepository.recoverLocalItemList() }
        verify (exactly = 0) { medicalListRepository.saveLocalItemList(any()) }
    }
}